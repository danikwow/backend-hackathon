package com.digital_guru.hackathonbackend.rest.controller;

import com.digital_guru.hackathonbackend.dto.QuestionDto;
import com.digital_guru.hackathonbackend.entity.question.Difficulty;
import com.digital_guru.hackathonbackend.entity.question.Question;
import com.digital_guru.hackathonbackend.mapper.QuestionMapper;
import com.digital_guru.hackathonbackend.repo.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * by Danil Koltovskikh at 28.03.2023
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("${jwt.data.easy.question}")
public class QuestionController {
    private final QuestionRepository questionRepository;
    private final QuestionMapper mapper;

    @PostMapping("${jwt.post}")
    public Mono<ResponseEntity> add(@RequestBody QuestionDto questionDto) {
        Question question = mapper.map(questionDto, new Question());
        return questionDto.getId() != null && questionDto.getId() != 0
                ? Mono.just(new ResponseEntity("wrong param: id MUST be null", HttpStatus.NOT_ACCEPTABLE))
                : Mono.just(questionRepository.save(question)).map(ResponseEntity::ok);
    }

    @PutMapping("${jwt.put}")
    public ResponseEntity update(@RequestBody QuestionDto questionDto) {
        return questionDto.getId() != null && questionDto.getId() != 0 ?
                ResponseEntity.ok(questionRepository.save(mapper.map(questionDto, new Question())))
                : ResponseEntity.badRequest().body("wrong param: id MUST NOT be null");
    }

    @GetMapping("${jwt.get}")
    public Mono<ResponseEntity<Question>> findById(@PathVariable Long id) {
        return questionRepository.findById(id)
                .map(ResponseEntity::ok)
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }

    @DeleteMapping("${jwt.delete}")
    public Mono<ResponseEntity<Object>> deleteById(@PathVariable Long id) {
        return questionRepository.deleteById(id)
                .then(Mono.just(ResponseEntity.ok().build()))
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }

    @GetMapping("${jwt.get.all.easy}")
    public Mono<ResponseEntity<List<Question>>> getAllEasyQuestion() {
        return questionRepository.findAllByDifficulty(Difficulty.EASY.name())
                .collectList()
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
    @GetMapping("${jwt.get.all.intermediate}")
    public Mono<ResponseEntity<List<Question>>> getAllIntermediateQuestion() {
        return questionRepository.findAllByDifficulty(Difficulty.INTERMEDIATE.name())
                .collectList()
                .map(ResponseEntity::ok)
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }

    @GetMapping("${jwt.get.all.hard}")
    public Mono<ResponseEntity<List<Question>>> getAllHardQuestion() {
        return questionRepository.findAllByDifficulty(Difficulty.HARD.name())
                .collectList()
                .map(ResponseEntity::ok)
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }
}
