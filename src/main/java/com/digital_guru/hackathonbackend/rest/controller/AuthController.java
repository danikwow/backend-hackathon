//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.digital_guru.hackathonbackend.rest.controller;

import com.digital_guru.hackathonbackend.dto.AuthRequestDto;
import com.digital_guru.hackathonbackend.dto.AuthResponseDto;
import com.digital_guru.hackathonbackend.dto.UserDto;
import com.digital_guru.hackathonbackend.entity.User;
import com.digital_guru.hackathonbackend.mapper.UserMapperImpl;
import com.digital_guru.hackathonbackend.security.CustomPrincipal;
import com.digital_guru.hackathonbackend.security.SecurityService;
import com.digital_guru.hackathonbackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("${jwt.auth}")
public class AuthController {
    private final SecurityService securityService;
    private final UserService userService;
    private final UserMapperImpl userMapper;

    @PostMapping("${jwt.auth.registration}")
    public Mono<UserDto> register(@RequestBody UserDto dto) {
        User entity = this.userMapper.map(dto);
        Mono<User> var10000 = this.userService.registerUser(entity);
        UserMapperImpl var10001 = this.userMapper;
        Objects.requireNonNull(var10001);
        return var10000.map(var10001::map);
    }

    @PostMapping("${jwt.auth.login}")
    public Mono<AuthResponseDto> login(@RequestBody AuthRequestDto dto) {
        return this.securityService.authenticate(dto.getUsername(), dto.getPassword()).flatMap((tokenDetails) -> {
            return Mono.just(AuthResponseDto.builder().id(tokenDetails.getUserId()).token(tokenDetails.getToken()).issuedAt(tokenDetails.getIssuedAt()).expiredAt(tokenDetails.getExpiresAt()).build());
        });
    }

    @GetMapping("${jwt.auth.info}")
    public Mono<UserDto> getUserInfo(Authentication authentication) {
        CustomPrincipal customPrincipal = (CustomPrincipal)authentication.getPrincipal();
        Mono<User> var10000 = this.userService.getUserById(customPrincipal.getId());
        UserMapperImpl var10001 = this.userMapper;
        Objects.requireNonNull(var10001);
        return var10000.map(var10001::map);
    }
}
