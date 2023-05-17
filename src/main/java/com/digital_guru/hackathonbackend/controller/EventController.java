package com.digital_guru.hackathonbackend.controller;

import com.digital_guru.hackathonbackend.entity.Event;
import com.digital_guru.hackathonbackend.repo.EventRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/event")
public class EventController {
    private final EventRepository eventRepository;

    public EventController(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }
    @PostMapping("/add")
    public ResponseEntity<Event> add(@RequestBody Event event) {
        return ResponseEntity.ok(eventRepository.save(event));
    }

    @PutMapping("/update")
    public ResponseEntity<Event> update(@RequestBody Event event) {
        return ResponseEntity.ok(eventRepository.save(event));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Event> findById(@PathVariable Integer id) {
        Event event;
        try {
            event = eventRepository.findById(id).get();
        }catch (NoSuchElementException e) {
            e.printStackTrace();
            return new ResponseEntity("id = " + id + " not found.", HttpStatus.NOT_ACCEPTABLE);
        }
        return ResponseEntity.ok(event);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteById(@PathVariable Integer id) {
        try {
            eventRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return new ResponseEntity("id: " + id + " no found.", HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity(HttpStatus.OK);
    }
}
