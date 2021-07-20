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
    private String id;
    private String password;
    public UserSigninRequestDto(String id, String password){
        this.id = id;
        this.password = password;
    }
    public User toEntity(){
        return User.builder()
                .id(id)
                .password(password)
                .roles(Collections.singletonList(Role.ROLE_CLIENT))
                .build();
    }
}
