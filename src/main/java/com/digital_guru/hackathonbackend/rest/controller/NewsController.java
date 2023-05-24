//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.digital_guru.hackathonbackend.rest.controller;

import com.digital_guru.hackathonbackend.entity.News;
import com.digital_guru.hackathonbackend.repo.NewsRepository;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("${jwt.data.news}")
public class NewsController {
    private final NewsRepository newsRepository;

    @PostMapping("${jwt.post}")
    public Mono<? extends ResponseEntity> add(@RequestBody News news) {
        if (news.getId() != null && news.getId() != 0L) {
            return Mono.just(new ResponseEntity("wrong param: id MUST be null", HttpStatus.BAD_REQUEST));
        } else if (news.getNewsName() == null) {
            return Mono.just(new ResponseEntity("wrong param: Name must not be null", HttpStatus.BAD_REQUEST));
        } else {
            return news.getNewsDescription() == null ? Mono.just(new ResponseEntity("wrong param: Description must not be null", HttpStatus.BAD_REQUEST)) : this.newsRepository.save(news).map((savedNews) -> {
                return ResponseEntity.ok(savedNews);
            });
        }
    }

    @PutMapping("${jwt.put}")
    public Mono<? extends ResponseEntity> update(@RequestBody News news) {
        if (news.getId() != null && news.getId() != 0L) {
            return news.getNewsName() != null && news.getNewsDescription() != null && news.getNewsDescription().length() >= 5 && news.getNewsName().length() <= 100 && news.getNewsDescription().length() <= 400 ? this.newsRepository.findById(news.getId()).flatMap((existingNews) -> {
                existingNews.setNewsName(news.getNewsName());
                existingNews.setNewsDescription(news.getNewsDescription());
                return this.newsRepository.save(existingNews).map((updatedNews) -> {
                    return ResponseEntity.ok(updatedNews);
                });
            }).switchIfEmpty(Mono.error(new NoSuchElementException("news not found"))) : Mono.just(new ResponseEntity("wrong param", HttpStatus.BAD_REQUEST));
        } else {
            return Mono.just(new ResponseEntity("wrong param: id MUST NOT be null", HttpStatus.BAD_REQUEST));
        }
    }

    @GetMapping("${jwt.get}")
    public Mono<ResponseEntity<News>> findById(@PathVariable Long id) {
        return this.newsRepository.findById(id).map((news) -> {
            return ResponseEntity.ok(news);
        }).defaultIfEmpty(new ResponseEntity(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("${jwt.delete}")
    public Mono<ResponseEntity> deleteById(@PathVariable Long id) {
        return this.newsRepository.deleteById(id).then(Mono.just(new ResponseEntity(HttpStatus.OK))).onErrorResume((e) -> {
            return Mono.just(new ResponseEntity(HttpStatus.NOT_FOUND));
        });
    }

    @GetMapping("${jwt.get.all}")
    public Mono<ResponseEntity<List<News>>> getAllNews() {
        return this.newsRepository.findAll().collectList().map((news) -> {
            return ResponseEntity.ok(news);
        }).switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }

    public NewsController(final NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }
}
