package com.thomaskavi.agendaki.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.thomaskavi.agendaki.entities.Appointment;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

}
