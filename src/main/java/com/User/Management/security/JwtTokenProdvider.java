package com.User.Management.security;

import com.User.Management.domain.Entity.Role;
import com.User.Management.service.UserService;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
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
    private String secretKey = "#SECRET-KEY설정";

    //
    private final static Long TOKEN_VAILD_TIME = 1000L * 60; // 1분
    private final static Long REFRESH_TOKEN_VAILD_TIME = 1000L * 120; // 1시간
    private final UserService userDetailService;
    private final UserDetailsService userDetailsService;

    @PostConstruct
    protected void init(){ //의존성 주입이 이루어진 후 초기화를 수행하는 메소드
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
        // secretKey를 Base64(암호화 알고리즘)으로 인코딩(암호화) 시킨다.
    }
    public String createToken(String userEmail, List<Role> roles){
        Claims claims = Jwts.claims().setSubject(userEmail); // JWT payload에 저장되는 정보단위
        claims.put("auth", roles.stream()
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

    public Authentication getAuthentication(String token){
        UserDetails userDetails = userDetailsService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    private String getUsername(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }
    public String resolveToken(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer")){
            return bearerToken.substring(7);
        }else{
            return null;
        }
}

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
