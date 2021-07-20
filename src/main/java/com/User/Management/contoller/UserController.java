package com.User.Management.contoller;

import com.User.Management.domain.dto.UserSigninRequestDto;
import com.User.Management.domain.dto.UserSignupDto;
import com.User.Management.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public String Signup(@RequestBody UserSignupDto userSignupDto){
        userService.signup(userSignupDto);
        return "회원가입에 성공하였습니다";
    }

    @PostMapping("/login")
    public String login(@RequestBody UserSigninRequestDto userSigninRequestDto){
        return userService.login(userSigninRequestDto);
    }
}
// EZY-SERVER 코드 보고 SecurityConfiguration에다가 특정 도메인 권한 없이 출입 허락하기