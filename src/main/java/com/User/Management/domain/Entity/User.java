package com.User.Management.domain.Entity;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class User implements UserDetails {

    @Id @GeneratedValue
    @Column(name = "USER_IDX")
    private Long idx;

    @Column(name = "USER_EMAIL")
    private String email;

    @Column(name = "USER_NAME")
    private String username;

    @Column(name = "USER_PASSWORD")
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    @Column(name="ROLE")
    private List<Role> roles = new ArrayList<>();

    public Collection<? extends GrantedAuthority> getAuthorities(){
        List<String> rolesConvertString = this.roles.stream().map(Enum::name).collect(Collectors.toList());
        return rolesConvertString.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }
    public void update(String email, String PW){
        this.email = email;
        this.password = PW;
    }
//    @Builder
//    public User(String email, String password,String username, List<Role> roles){
//        this.email = email;
//        this.password = password;
//        this.username=username;
//        this.roles = roles;
//    }

    @Override
    public boolean isAccountNonExpired() { // 계정이 만료되지 않았는 지를 리턴한다 ( true : 만료안됨)
        return true;
    }

    @Override
    public boolean isAccountNonLocked() { // 계정이 잠기지 않았는 지를 리턴한다 ( true : 잠기지 않음)
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() { // 비밀번호가 만료되지 않았는 지를 리턴한다 ( true: 만료안됨)
        return true;
    }

    @Override
    public boolean isEnabled() { // 계정이 활성화(사용 가능)인지를 리턴한다 (true:활성화)
        return true;
    }
}
