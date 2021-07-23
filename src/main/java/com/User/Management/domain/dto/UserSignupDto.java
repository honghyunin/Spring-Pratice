package com.User.Management.domain.dto;

import com.User.Management.domain.Entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserSignupDto {
    private String email;
    private String password;
    private String username;
    public UserSignupDto(String email, String password, String username){
        this.email = email;
        this.password = password;
        this.username = username;
    }
    public User toEntity(){
        return User.builder()
                .email(email)
                .password(password)
                .username(username)
                .build();
    }
}
