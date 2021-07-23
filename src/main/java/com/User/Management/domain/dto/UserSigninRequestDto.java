package com.User.Management.domain.dto;


import com.User.Management.domain.Entity.Role;
import com.User.Management.domain.Entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collections;

@Getter @Setter
@NoArgsConstructor
@Builder
public class UserSigninRequestDto {
    private String email;
    private String password;
    public UserSigninRequestDto(String email, String password){
        this.email = email;
        this.password = password;
    }
    public User toEntity(){
        return User.builder()
                .email(this.getEmail())
                .password(this.getPassword())
                .roles(Collections.singletonList(Role.ROLE_CLIENT))
                .build();
    }
}
