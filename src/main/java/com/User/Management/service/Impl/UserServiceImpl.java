package com.User.Management.service.Impl;

import com.User.Management.advice.exception.NotMatchPasswordException;
import com.User.Management.advice.exception.UserAlreadyExistsException;
import com.User.Management.advice.exception.UserNotFoundException;
import com.User.Management.domain.Entity.User;
import com.User.Management.domain.dto.UserSigninRequestDto;
import com.User.Management.domain.dto.UserSignupDto;
import com.User.Management.repository.UserRepository;
import com.User.Management.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public User signup(UserSignupDto userSignupDto) {
        if (userRepository.findByName(userSignupDto.getId()) != null) {
            throw new UserAlreadyExistsException();
        }
        userSignupDto.setPassword(passwordEncoder.encode(userSignupDto.getPassword()));
        userRepository.save(userSignupDto.toEntity());
        return userRepository.save(userSignupDto.toEntity());
    }

    @Override
    public String login(UserSigninRequestDto user) {
        User findUser = userRepository.findById(user.getId());
        if(findUser == null) throw new UserNotFoundException("유저를 찾을 수 없습니다");

        if(userRepository.findByPassword(user.getPassword()).equals(user.getPassword())) throw new NotMatchPasswordException("비밀번호가 일치하지 않습니다");
        return "로그인이 완료되었습니다";
    }



}

