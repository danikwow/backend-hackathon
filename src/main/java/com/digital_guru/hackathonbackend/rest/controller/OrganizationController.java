package com.digital_guru.hackathonbackend.rest.controller;

import com.digital_guru.hackathonbackend.entity.Organization;
import com.digital_guru.hackathonbackend.repo.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * by Danil Koltovskikh at 26.03.2023
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("${jwt.data.organization}")
public class OrganizationController {
    private final OrganizationRepository organizationRepository;

    @PostMapping("${jwt.post}")
    public Mono<? extends ResponseEntity> add(@RequestBody Organization organization) {
        return organization.getId() != null && organization.getId() != 0 ?
                Mono.just(new ResponseEntity("wrong param: id MUST be null", HttpStatus.BAD_REQUEST)) :
                organizationRepository.save(organization).map(ResponseEntity::ok);
    }

    @PutMapping("${jwt.put}")
    public Mono<ResponseEntity<Organization>> update(@RequestBody Organization organization) {
        return organization.getId() != null && organization.getId() != 0 ?
                organizationRepository.findById(organization.getId()).flatMap((existingOrganization) -> {

                    existingOrganization.setName(organization.getName());
                    existingOrganization.setDescription(organization.getDescription());
            return organizationRepository.save(existingOrganization).map(ResponseEntity::ok);

        }).switchIfEmpty(Mono.error(new NoSuchElementException("organization not found")))
                : Mono.just(new ResponseEntity("wrong param: id MUST NOT be null", HttpStatus.BAD_REQUEST));
    }

    @GetMapping("${jwt.get}")
    public Mono findById(@PathVariable Integer id) {
        return organizationRepository.findById(id).map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("${jwt.delete}")
    public Mono<ResponseEntity> deleteById(@PathVariable Integer id) {
        return organizationRepository.deleteById(id).then(Mono.just(new ResponseEntity(HttpStatus.OK)))
                .onErrorResume((e) -> Mono.just(new ResponseEntity(HttpStatus.NOT_FOUND)));
    }

    @GetMapping("${jwt.get.all}")
    public Mono<ResponseEntity<List<Organization>>> getAllEvents() {
        return organizationRepository.findAll().collectList().map(ResponseEntity::ok)
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }
}
