package com.digital_guru.hackathonbackend.entity;

import jakarta.persistence.*;
import jdk.jfr.BooleanFlag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    @Column(name = "first_name", nullable = true, length = 30)
    private String firstName;
    @Basic
    @Column(name = "second_name", nullable = true, length = 30)
    private String secondName;
    @Basic
    @Column(name = "last_name", nullable = true, length = 30)
    private String lastName;
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
