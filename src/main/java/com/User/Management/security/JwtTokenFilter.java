package com.User.Management.security;

import com.User.Management.advice.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {
    private final JwtTokenProdvider jwtTokenProdvider;
    public JwtTokenFilter(JwtTokenProdvider jwtTokenProdvider) {
        this.jwtTokenProdvider = jwtTokenProdvider;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = jwtTokenProdvider.resolveToken(request); // resolveToken으로 암호화된 토큰인지 아닌지 검사한 후 token으로 처리
        try{
            if(token != null && jwtTokenProdvider.validateToken(token)){ // 토큰이 비어있거나 토큰이 유효한지 검사
                Authentication authentication = jwtTokenProdvider.getAuthentication(token);// 토큰안에 유저의 정보를 검증하고
                SecurityContextHolder.getContext().setAuthentication(authentication); // 검증된 토큰을 컨텍스트에 저장
            }
        }catch (CustomException e){
            SecurityContextHolder.clearContext(); // 토큰이 유효하지 않으면 컨텍스트를 clean
            response.sendError(e.getHttpstatus().value(),e.getMessage()); // 에러 응답을 보내줌
            return;
        }
        filterChain.doFilter(request, response); // doFilter() 로 이 메소드를 통해 요청과 응답을 처리함
        // FilterChain이란 필터들이 모여 하나의 체인으로 형성되는 것을 의미함
        //Filter는 요청(Request)과 응답(Response)에 대한 정보들을 변경할 수 있게 개발자들에게 제공하는 서블린 컨테이너 입니다.
    }
}
