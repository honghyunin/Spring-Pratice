package com.User.Management.dto;


import com.User.Management.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
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
                .build();
    }
}
