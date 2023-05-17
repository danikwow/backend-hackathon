package com.digital_guru.hackathonbackend.repo;

import com.digital_guru.hackathonbackend.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Integer> {
}
