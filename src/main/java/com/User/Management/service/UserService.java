package com.User.Management.service;


import com.User.Management.domain.User;
import com.User.Management.dto.UserSigninRequestDto;
import com.User.Management.dto.UserSignupDto;

public interface UserService {
    User signup(UserSignupDto user);
    String login(UserSigninRequestDto user);
}
