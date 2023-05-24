//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.digital_guru.hackathonbackend.rest.controller;

import com.digital_guru.hackathonbackend.entity.Organization;
import com.digital_guru.hackathonbackend.repo.OrganizationRepository;
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
@RequestMapping("${jwt.data.organization}")
public class OrganizationController {
    private final OrganizationRepository organizationRepository;

    public OrganizationController(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }

    @PostMapping("${jwt.post}")
    public Mono<? extends ResponseEntity> add(@RequestBody Organization organization) {
        return organization.getId() != null && organization.getId() != 0 ? Mono.just(new ResponseEntity("wrong param: id MUST be null", HttpStatus.BAD_REQUEST)) : this.organizationRepository.save(organization).map((savedOrganization) -> {
            return ResponseEntity.ok(savedOrganization);
        });
    }

    @PutMapping("${jwt.put}")
    public Mono<? extends ResponseEntity> update(@RequestBody Organization organization) {
        return organization.getId() != null && organization.getId() != 0 ? this.organizationRepository.findById(organization.getId()).flatMap((existingOrganization) -> {
            existingOrganization.setName(organization.getName());
            existingOrganization.setDescription(organization.getDescription());
            return this.organizationRepository.save(existingOrganization).map((updatedOrganization) -> {
                return ResponseEntity.ok(updatedOrganization);
            });
        }).switchIfEmpty(Mono.error(new NoSuchElementException("organization not found"))) : Mono.just(new ResponseEntity("wrong param: id MUST NOT be null", HttpStatus.BAD_REQUEST));
    }

    @GetMapping("${jwt.get}")
    public Mono<ResponseEntity<Organization>> findById(@PathVariable Integer id) {
        return this.organizationRepository.findById(id).map((organization) -> {
            return ResponseEntity.ok(organization);
        }).defaultIfEmpty(new ResponseEntity(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("${jwt.delete}")
    public Mono<ResponseEntity> deleteById(@PathVariable Integer id) {
        return this.organizationRepository.deleteById(id).then(Mono.just(new ResponseEntity(HttpStatus.OK))).onErrorResume((e) -> {
            return Mono.just(new ResponseEntity(HttpStatus.NOT_FOUND));
        });
    }

    @GetMapping("${jwt.get.all}")
    public Mono<ResponseEntity<List<Organization>>> getAllEvents() {
        return this.organizationRepository.findAll().collectList().map((events) -> {
            return ResponseEntity.ok(events);
        }).switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }
}
