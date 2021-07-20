package com.User.Management.service;


import com.User.Management.domain.Entity.User;
import com.User.Management.domain.dto.UserSigninRequestDto;
import com.User.Management.domain.dto.UserSignupDto;

public interface UserService {
    User signup(UserSignupDto user);
    String login(UserSigninRequestDto user);
}
