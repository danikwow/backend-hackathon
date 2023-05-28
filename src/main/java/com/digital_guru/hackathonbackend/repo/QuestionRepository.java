package com.digital_guru.hackathonbackend.repo;

import com.digital_guru.hackathonbackend.entity.question.Question;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface QuestionRepository extends R2dbcRepository<Question, Long> {
    Flux<Question> findAllByDifficulty(String difficulty);
}
