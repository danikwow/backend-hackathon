package com.digital_guru.hackathonbackend.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
@Entity
@Table(name = "news", schema = "schooldb")
public class News {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @org.springframework.data.annotation.Id
    @Column(name = "id", nullable = false)
    private Long id;
    @Basic
    @Column(name = "news_name", nullable = false, length = 100)
    private String newsName;
    @Basic
    @Column(name = "news_description", nullable = false, length = 400)
    private String newsDescription;
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic
    @Temporal(TemporalType.DATE)
    @Column(name = "create_date_time")
    private LocalDateTime createDateTime;
    @Basic
    @Column(name = "start_date_time")
    private LocalDateTime startDateTime;
}
