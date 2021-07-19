package com.User.Management.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@NoArgsConstructor
@Entity
public class User {

    @Id @GeneratedValue
    @Column(name = "USER_IDX")
    private Long idx;

    @Column(name = "USER_EMAIL")
    private String email;

    @Column(name = "USER_ID")
    private String id;

    @Column(name = "USER_NAME")
    private String name;

    @Column(name = "USER_PASSWORD")
    private String password;

    @Builder
    User(Long idx, String email, String id, String name, String password){
        this.idx = idx;
        this.email = email;
        this.id = id;
        this.name = name;
        this.password = password;
    }
}
