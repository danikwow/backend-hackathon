package com.digital_guru.hackathonbackend.rest.controller;

import com.digital_guru.hackathonbackend.dto.AuthRequestDto;
import com.digital_guru.hackathonbackend.dto.AuthResponseDto;
import com.digital_guru.hackathonbackend.dto.UserDto;
import com.digital_guru.hackathonbackend.entity.User;
import com.digital_guru.hackathonbackend.mapper.UserMapper;
import com.digital_guru.hackathonbackend.security.CustomPrincipal;
import com.digital_guru.hackathonbackend.security.SecurityService;
import com.digital_guru.hackathonbackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Objects;

/**
 * by Danil Koltovskikh at 26.03.2023
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("${jwt.auth}")
public class AuthController {
    private final SecurityService securityService;
    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping("${jwt.auth.registration}")
    public Mono<UserDto> register(@RequestBody UserDto dto) {
        User entity = userMapper.map(dto);
        Mono<User> userMono = userService.registerUser(entity);
        UserMapper localMap = userMapper;
        Objects.requireNonNull(localMap);
        return userMono.map(localMap::map);
    }

    @PostMapping("${jwt.auth.login}")
    public Mono<AuthResponseDto> login(@RequestBody AuthRequestDto dto) {
        return securityService.authenticate(dto.getUsername(), dto.getPassword())
                .flatMap((tokenDetails) -> Mono.just(AuthResponseDto.builder().id(tokenDetails.getUserId())
                        .token(tokenDetails.getToken())
                        .issuedAt(tokenDetails.getIssuedAt())
                        .expiredAt(tokenDetails.getExpiresAt())
                        .build()));
    }

    @GetMapping("${jwt.auth.info}")
    public Mono<UserDto> getUserInfo(Authentication authentication) {
        CustomPrincipal customPrincipal = (CustomPrincipal) authentication.getPrincipal();
        Mono<User> monoDto = userService.getUserById(customPrincipal.getId());
        UserMapper localMap = userMapper;
        Objects.requireNonNull(localMap);
        return monoDto.map(localMap::map);
    }
}
