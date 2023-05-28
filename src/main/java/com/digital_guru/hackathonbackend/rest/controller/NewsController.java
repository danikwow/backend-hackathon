package com.digital_guru.hackathonbackend.rest.controller;

import com.digital_guru.hackathonbackend.entity.News;
import com.digital_guru.hackathonbackend.repo.NewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * by Danil Koltovskikh at 26.03.2023
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("${jwt.data.news}")
public class NewsController {
    private final NewsRepository newsRepository;

    @PostMapping("${jwt.post}")
    public Mono<? extends ResponseEntity> add(@RequestBody News news) {
        return (news.getId() != null && news.getId() != 0L) ?
                Mono.just(new ResponseEntity("wrong param: id MUST be null", HttpStatus.BAD_REQUEST)) :
                (news.getNewsName() == null) ?
                        Mono.just(new ResponseEntity("wrong param: Name must not be null", HttpStatus.BAD_REQUEST)) :
                        (news.getNewsDescription() == null) ?
                                Mono.just(new ResponseEntity("wrong param: Description must not be null", HttpStatus.BAD_REQUEST)) :
                                newsRepository.save(news).map(ResponseEntity::ok);
    }

    @PutMapping("${jwt.put}")
    public Mono<ResponseEntity> update(@RequestBody News news) {
        return newsRepository.findById(news.getId())
                .flatMap((existNews) -> {
                    existNews.setNewsName(news.getNewsName());
                    existNews.setNewsDescription(news.getNewsDescription());
                    existNews.setStartDateTime(news.getStartDateTime());
                    return newsRepository.save(existNews);
                })
                .flatMap(savedNews -> Mono.just(new ResponseEntity(HttpStatus.OK)))
                .switchIfEmpty(Mono.just(new ResponseEntity(HttpStatus.NOT_FOUND)));
    }


    @GetMapping("${jwt.get}")
    public Mono findById(@PathVariable Long id) {
        return newsRepository.findById(id).map(ResponseEntity::ok)
                .defaultIfEmpty(new ResponseEntity(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("${jwt.delete}")
    public Mono<ResponseEntity<Object>> deleteById(@PathVariable Long id) {
        return newsRepository.deleteById(id).then(Mono.just(ResponseEntity.ok().build()))
                .onErrorResume((e) -> Mono.just(ResponseEntity.notFound().build()));
    }

    @GetMapping("${jwt.get.all}")
    public Mono<ResponseEntity<List<News>>> getAllNews() {
        return newsRepository.findAll().collectList().map(ResponseEntity::ok)
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }

}
