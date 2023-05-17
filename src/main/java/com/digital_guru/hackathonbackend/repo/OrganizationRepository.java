package com.digital_guru.hackathonbackend.repo;

import com.digital_guru.hackathonbackend.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizationRepository extends JpaRepository<Organization, Integer> {
}
