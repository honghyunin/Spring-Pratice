package com.User.Management.service.Impl;

import com.User.Management.advice.exception.NotMatchPasswordException;
import com.User.Management.advice.exception.UserAlreadyExistsException;
import com.User.Management.advice.exception.UserNotFoundException;
import com.User.Management.domain.Entity.User;
import com.User.Management.domain.dto.UserSigninRequestDto;
import com.User.Management.domain.dto.UserSignupDto;
import com.User.Management.repository.UserRepository;
import com.User.Management.security.JwtTokenProdvider;
import com.User.Management.service.UserService;
import com.User.Management.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProdvider jwtTokenProdvider;
    private final RedisUtil redisUtil;
    @Override
    public User signup(UserSignupDto userSignupDto) {
        if (userRepository.findByEmail(userSignupDto.getEmail()) != null) {
            throw new UserAlreadyExistsException();
        }
        userSignupDto.setPassword(passwordEncoder.encode(userSignupDto.getPassword()));
        return userRepository.save(userSignupDto.toEntity());
    }

    @Override
    public Map<String, String> login(UserSigninRequestDto user) {
        User findUser = userRepository.findByEmail(user.getEmail());
        if(findUser == null) throw new UserNotFoundException("유저를 찾을 수 없습니다");
        boolean passwordChcek = passwordEncoder.matches(user.getPassword(), findUser.getPassword()); // 언코딩 후 dto에 저장된 pw갑과 db에 저장된 pw를 비교 후 같으면 true 다르면 false 반환

        if(!passwordChcek) throw new NotMatchPasswordException("올바르지 않은 비밀번호입니다.");

        String accessToken = jwtTokenProdvider.createToken(user.getEmail(),user.toEntity().getRoles());
        String refreshToken = jwtTokenProdvider.createRefreshToken();

        redisUtil.deleteData(user.getEmail()); // redis에 값을 삽입하기 전 해당 아이디의 refreshToken 삭제
        redisUtil.setDataExpire(findUser.getEmail(), refreshToken, JwtTokenProdvider.REFRESH_TOKEN_VAILD_TIME);

        Map<String, String> map = new HashMap<>(); // 값을 반환할 id, pw를 담을 수 있는 map 생성
        map.put("email", user.getEmail());
        map.put("accessToken", "Bearer "+accessToken);
        map.put("refreshToken", "Bearer "+refreshToken);

        return map;
    }



}

