package com.User.Management.service;


import com.User.Management.domain.Entity.User;
import com.User.Management.domain.dto.UserSigninRequestDto;
import com.User.Management.domain.dto.UserSignupDto;

import java.util.Map;

public interface UserService {
    User signup(UserSignupDto user);
    Map<String, String> login(UserSigninRequestDto user);
}
