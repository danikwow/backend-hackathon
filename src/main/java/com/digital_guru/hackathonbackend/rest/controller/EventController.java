package com.digital_guru.hackathonbackend.rest.controller;

import com.digital_guru.hackathonbackend.entity.Event;
import com.digital_guru.hackathonbackend.repo.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * by Danil Koltovskikh at 26.03.2023
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("${jwt.data.event}")
public class EventController {
    private final EventRepository eventRepository;

    @PostMapping("${jwt.post}")
    public Mono<ResponseEntity<Event>> add(@RequestBody Event event) {
        return event.getId() != null && event.getId() != 0
                ? Mono.just(new ResponseEntity("wrong param: id MUST be null", HttpStatus.NOT_ACCEPTABLE))
                : eventRepository.save(event).map(ResponseEntity::ok);
    }

    @PutMapping("${jwt.put}")
    public Mono<Event> update(@RequestBody Event event) {
        return event.getId() != null && event.getId() != 0 ? eventRepository.findById(event.getId())
                .flatMap((existingEvent) -> {
            existingEvent.setName(event.getName());
            existingEvent.setDescription(event.getDescription());
            existingEvent.setStartDate(event.getStartDate());
            existingEvent.setEndDate(event.getEndDate());
            return eventRepository.save(existingEvent);
        }).switchIfEmpty(Mono.error(
                new ResponseStatusException(HttpStatus.NOT_FOUND, "event not found")))
                : Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "wrong param: id MUST NOT be null"));
    }

    @GetMapping("${jwt.get}")
    public Mono<Event> findById(@PathVariable Integer id) {
        return eventRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found")));
    }

    @DeleteMapping("${jwt.delete}")
    public Mono<? extends ResponseEntity> deleteById(@PathVariable Integer id) {
        return eventRepository.deleteById(id)
                .then(Mono.just(new ResponseEntity(HttpStatus.OK)))
                .onErrorResume((e) -> Mono.just(new ResponseEntity(HttpStatus.NOT_FOUND)));
    }

    @GetMapping("${jwt.get.all}")
    public Mono<ResponseEntity<List<Event>>> getAllEvents() {
        return eventRepository.findAll().collectList()
                .map(ResponseEntity::ok)
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }
}
