package com.digital_guru.hackathonbackend.controller;

import com.digital_guru.hackathonbackend.entity.News;
import com.digital_guru.hackathonbackend.repo.NewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping(name = "/news")
@RequiredArgsConstructor
public class NewsController {

    private final NewsRepository newsRepository;

    @PostMapping("/add")
    public ResponseEntity<News> add(@RequestBody News news) {

        return ResponseEntity.ok(newsRepository.save(news));
    }

    @PutMapping("/update")
    public ResponseEntity<News> update(@RequestBody News news) {
        if (validateNews(news) != null) {
            return validateNews(news);
        }
        return ResponseEntity.ok(newsRepository.save(news));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<News> findById(@PathVariable Long id) {
        News news;
        try {
            news = newsRepository.findById(id).get();
        }catch (NoSuchElementException e) {
            e.printStackTrace();
            return new ResponseEntity("id = " + id + " not found.", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(news);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteById(@PathVariable Long id) {
        try {
            newsRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return new ResponseEntity("id: " + id + " no found.", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity getAllOrganization() {
        List<News> allOrganization = new ArrayList<>();
        try {
            allOrganization.addAll(newsRepository.findAll());
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return new ResponseEntity("not found", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(allOrganization);
    }

    private ResponseEntity validateNews(News news) {
        if (news.getId() != null && news.getId() != 0) {
            return new ResponseEntity("wrong param: id MUST be null", HttpStatus.BAD_REQUEST);
        }
        if (news.getNewsName() == null) {
            return new ResponseEntity("wrong param: Name must not be null", HttpStatus.BAD_REQUEST);
        }
        if (news.getNewsDescription() == null) {
            return new ResponseEntity("wrong param: Description must not be null", HttpStatus.BAD_REQUEST);
        }
        return null;
    }
}
