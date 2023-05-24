package com.digital_guru.hackathonbackend.entity;

import jakarta.persistence.*;
import jdk.jfr.BooleanFlag;
import lombok.*;


import java.time.LocalDateTime;

@NoArgsConstructor
@Entity
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@Table(name = "user", schema = "schooldb")
public class User {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @org.springframework.data.annotation.Id
    @Column(name = "id", nullable = false)
    private Long id;
    @Basic
    @Column(name = "username", nullable = false, length = 16)
    private String username;
    @Basic
    @Column(name = "email", nullable = true, length = 255)
    private String email;
    @Basic
    @Enumerated(EnumType.STRING)
    @Column(name = "user_role")
    private UserRole userRole;
    @Basic
    @Column(name = "password", nullable = false, length = 32)
    private String password;
    @Basic
    @Column(name = "create_time", nullable = true)
    private LocalDateTime createTime;
    @Basic
    @Column(name = "update_time", nullable = true)
    private LocalDateTime updateTime;
    @Basic
    @BooleanFlag
    private boolean enabled;
}
