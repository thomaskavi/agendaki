package com.thomaskavi.agendaki.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.thomaskavi.agendaki.entities.Professional;

@Repository
public interface ProfessionalRepository extends JpaRepository<Professional, Long> {

}
