package com.User.Management.security;

import com.User.Management.domain.Entity.Role;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class JwtTokenProdvider {
    @Value("${security.jwt.token.secretKey}") // 프로퍼티 파일의 값을 가져와 필드에 대입시켜주는 어노테이션
    private String secretKey; // 이 필드를 통해 아이디 로그인 인증이 끝난 후 다시 요청이 들어올 때 요청이 유효한지 검증함

    // 토큰의 유효시간 설정
    public final static Long TOKEN_VAILD_TIME = 1000L * 60; // 1분
    public final static Long REFRESH_TOKEN_VAILD_TIME = 1000L * 120; // 1시간

    private final MyUserDetails myUserDetails; // DB에서 유저정보를 가져오는 역할을 함

    @PostConstruct //다른 리소스에서 호출되지 않아도 의존성 주입이 끝난 뒤 수행하게 하는 어노테이션
    protected void init(){ //의존성 주입이 이루어진 후 초기화를 수행하는 메소드
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
        // secretKey를 Base64(암호화 알고리즘)으로 인코딩(암호화) 시킨다.
    }
    public String createToken(String userId, List<Role> roles){
        Claims claims = Jwts.claims().setSubject(userId); // claims : JWT payload에 저장되는 정보단위
        claims.put("auth", roles.stream() // claims 에 권한 정보를 추가
        .map(s -> new SimpleGrantedAuthority(s.getAuthority()))
        .filter(Objects::nonNull).collect(Collectors.toList()));

        Date now = new Date();
        Date validity = new Date(now.getTime() + TOKEN_VAILD_TIME);// 유효시간 선언 [ 지금시간 + expireTime 시간 ]
        return Jwts.builder()
                .setClaims(claims) //정보저장
                .setIssuedAt(now) // 토큰 바랭 시간 정보
                .setExpiration(validity) // set Expire Time
                .signWith(SignatureAlgorithm.HS256, secretKey) // 사용할 암호화 알고리즘과 signature에 들어갈 secret값 세팅팅
               .compact();
    }
    public String createRefreshToken(){
        Claims claims = Jwts.claims().setSubject(null);

        Date now = new Date();
        Date validity = new Date(now.getTime() + REFRESH_TOKEN_VAILD_TIME); //Expire Time + 지금시간

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }
    //유저 정보를 가져오는 메소드
    public Authentication getAuthentication(String token){
        UserDetails userDetails = myUserDetails.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    private String getUsername(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }
    // Request의 header에서 token 값을 가져오는 메소드
    public String resolveToken(HttpServletRequest request){ //
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer")){
            return bearerToken.substring(7);
        }else{
            return null;
        }
}
    // token이 유효한지 아닌지 검증하는 메소드
    public boolean validateToken(String token) {
        try{
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch(Exception e) {
            SecurityContextHolder.clearContext();
            return false;
        }
    }
}
