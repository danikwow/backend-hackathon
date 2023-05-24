package com.digital_guru.hackathonbackend.repo;

import com.digital_guru.hackathonbackend.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsRepository extends R2dbcRepository<News, Long> {
}
