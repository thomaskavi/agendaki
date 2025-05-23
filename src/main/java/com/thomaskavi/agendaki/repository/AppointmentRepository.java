package com.thomaskavi.agendaki.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.thomaskavi.agendaki.entities.Appointment;
import com.thomaskavi.agendaki.entities.User;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

  List<Appointment> findByClient(User client);

  List<Appointment> findByProfessional(User professional);

}
