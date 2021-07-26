package com.User.Management.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity(debug = true) // 웹 보안을 활성화 시키며 스프링 시큐리티의 WebSecurityConfigure 를 구현하거나 WebSecurityConfigureAdapter를 확장한 빈으로 설정되어 있어야 한다.
@Configuration // Configuration 어노테이션은 스프링 IOC Container에게 해당 클래스르 Bean 구성 Class임을 알려주는 것이다0
@RequiredArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final JwtTokenProdvider jwtTokenProdvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.csrf().and()
                .csrf() // CSRF 프로텍션 비활성화
                .and()
                .cors().disable()
                .httpBasic().disable() // HTTP 기본 인증 비활성화
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()// 리소스 별 허용 범위 설정
                .antMatchers("/api/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .apply(new JwtTokenFilterConfigurer(jwtTokenProdvider));

    }

    @Override
    public void configure(WebSecurity web) throws Exception{
        web.ignoring().antMatchers("/**api-docs","/swagger-resources/**","/swagger-ui.html","/webjars/**","/swagger/**","/configuration/ui","/api/**","/h2-console/**");
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(12);
    }
}
