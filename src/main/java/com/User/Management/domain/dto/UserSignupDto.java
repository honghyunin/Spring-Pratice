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
    private String name;
    private String id;
    private String password;

    public User toEntity(){
        return User.builder()
                .email(email)
                .name(name)
                .id(id)
                .password(password)
                .build();
    }
}
