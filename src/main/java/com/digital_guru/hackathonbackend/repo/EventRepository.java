package com.digital_guru.hackathonbackend.repo;

import com.digital_guru.hackathonbackend.entity.Event;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends R2dbcRepository<Event, Integer> {
}
