package com.thomaskavi.agendaki.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.thomaskavi.agendaki.entities.AvailableSlot;
import com.thomaskavi.agendaki.entities.Professional;

@Repository
public interface AvailableSlotRepository extends JpaRepository<AvailableSlot, Long> {

  List<AvailableSlot> findByProfessionalAndIsBookedFalse(Professional professional);

  // Verificar conflitos de horários
  boolean existsByProfessionalIdAndStartTimeLessThanEqualAndEndTimeGreaterThanEqual(
      Long professionalId, LocalDateTime endTime, LocalDateTime startTime);
}
