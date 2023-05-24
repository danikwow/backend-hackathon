package com.digital_guru.hackathonbackend.repo;

import com.digital_guru.hackathonbackend.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationRepository extends R2dbcRepository<Organization, Integer> {
}

