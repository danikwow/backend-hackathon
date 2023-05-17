package com.digital_guru.hackathonbackend.controller;


import com.digital_guru.hackathonbackend.entity.Organization;
import com.digital_guru.hackathonbackend.repo.OrganizationRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/organization")
public class OrganizationController {
    private final OrganizationRepository organizationRepository;

    public OrganizationController(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }
    
    @PostMapping("/add")
    public ResponseEntity<Organization> add(@RequestBody Organization organiztion) {
        return ResponseEntity.ok(organizationRepository.save(organiztion));
    }

    @PutMapping("/update")
    public ResponseEntity<Organization> update(@RequestBody Organization organiztion) {
        return ResponseEntity.ok(organizationRepository.save(organiztion));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Organization> findById(@PathVariable Integer id) {
        Organization organiztion;
        try {
            organiztion = organizationRepository.findById(id).get();
        }catch (NoSuchElementException e) {
            e.printStackTrace();
            return new ResponseEntity("id = " + id + " not found.", HttpStatus.NOT_ACCEPTABLE);
        }
        return ResponseEntity.ok(organiztion);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteById(@PathVariable Integer id) {
        try {
            organizationRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return new ResponseEntity("id: " + id + " no found.", HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity(HttpStatus.OK);
    }
}
