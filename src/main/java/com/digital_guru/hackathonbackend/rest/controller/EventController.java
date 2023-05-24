//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.digital_guru.hackathonbackend.rest;

import com.digital_guru.hackathonbackend.entity.Event;
import com.digital_guru.hackathonbackend.repo.EventRepository;
import java.util.List;
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
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("${jwt.data.event}")
public class EventController {
    private final EventRepository eventRepository;

    public EventController(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @PostMapping("${jwt.post}")
    public Mono<ResponseEntity<Event>> add(@RequestBody Event event) {
        return event.getId() != null && event.getId() != 0 ? Mono.just(new ResponseEntity("wrong param: id MUST be null", HttpStatus.NOT_ACCEPTABLE)) : this.eventRepository.save(event).map((savedEvent) -> {
            return ResponseEntity.ok(savedEvent);
        });
    }

    @PutMapping("${jwt.put}")
    public Mono<Event> update(@RequestBody Event event) {
        return event.getId() != null && event.getId() != 0 ? this.eventRepository.findById(event.getId()).flatMap((existingEvent) -> {
            existingEvent.setName(event.getName());
            existingEvent.setDescription(event.getDescription());
            existingEvent.setStartDate(event.getStartDate());
            existingEvent.setEndDate(event.getEndDate());
            return this.eventRepository.save(existingEvent);
        }).switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "event not found"))) : Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "wrong param: id MUST NOT be null"));
    }

    @GetMapping("${jwt.get}")
    public Mono<Event> findById(@PathVariable Integer id) {
        return this.eventRepository.findById(id).switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found")));
    }

    @DeleteMapping("${jwt.delete}")
    public Mono<? extends ResponseEntity> deleteById(@PathVariable Integer id) {
        return this.eventRepository.deleteById(id).then(Mono.just(new ResponseEntity(HttpStatus.OK))).onErrorResume((e) -> {
            return Mono.just(new ResponseEntity(HttpStatus.NOT_FOUND));
        });
    }

    @GetMapping("${jwt.get.all}")
    public Mono<ResponseEntity<List<Event>>> getAllEvents() {
        return this.eventRepository.findAll().collectList().map((events) -> {
            return ResponseEntity.ok(events);
        }).switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }
}
