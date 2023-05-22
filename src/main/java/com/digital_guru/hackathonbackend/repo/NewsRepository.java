package com.digital_guru.hackathonbackend.repo;

import com.digital_guru.hackathonbackend.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<News, Long> {
}
