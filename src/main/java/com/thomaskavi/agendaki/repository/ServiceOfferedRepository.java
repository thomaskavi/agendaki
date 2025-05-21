package com.thomaskavi.agendaki.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.thomaskavi.agendaki.entities.ServiceOffered;

@Repository
public interface ServiceOfferedRepository extends JpaRepository<ServiceOffered, Long> {

}
