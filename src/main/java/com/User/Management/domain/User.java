package com.User.Management.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class User {
    private String email;
    private String id;
    private String name;
    private String password;
}
