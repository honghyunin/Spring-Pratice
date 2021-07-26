package com.User.Management.security;

import com.User.Management.domain.Entity.User;
import com.User.Management.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyUserDetails implements UserDetailsService { // UserDetailService는 유저의 정보를 가져오는 인터페이스므로 이 클래스에선 username의 정보를 가져와  DB내에서 존재여부를 받아옴
    private final UserRepository userRepository; // DB안에 있는 정보를 담고있는 클래스
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);; // 반환형이 UserDetails이기 때문에 반환할 User 타입의 필드를 생성해주고 DB에서 username을 받아온다
        if(username == null){ // 만약 그 받아온 username이 null일 경우에 exception
            throw new UsernameNotFoundException("해당 유저를 찾을 수 없습니다");
        }
        return user;
    }
}
