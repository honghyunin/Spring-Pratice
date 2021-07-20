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
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {
    private final JwtTokenProdvider jwtTokenProdvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = jwtTokenProdvider.resolveToken(request);
        try{
            if(token != null && jwtTokenProdvider.validateToken(token)){
                Authentication authentication = jwtTokenProdvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            SecurityContextHolder.clearContext();
        }catch (CustomException e){
            response.sendError(e.getHttpstatus().value(),e.getMessage());
            return ;
        }
    }
}