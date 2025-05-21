package com.thomaskavi.agendaki.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.thomaskavi.agendaki.entities.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

}
