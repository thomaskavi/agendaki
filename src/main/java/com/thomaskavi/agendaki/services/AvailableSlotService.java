package com.thomaskavi.agendaki.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thomaskavi.agendaki.dto.AvailableSlotDTO;
import com.thomaskavi.agendaki.dto.CreateAvailableSlotDTO;
import com.thomaskavi.agendaki.entities.AvailableSlot;
import com.thomaskavi.agendaki.entities.Professional;
import com.thomaskavi.agendaki.repository.AvailableSlotRepository;
import com.thomaskavi.agendaki.services.exceptions.DatabaseException;

@Service
public class AvailableSlotService {

  @Autowired
  private AuthService authService;

  @Autowired
  private AvailableSlotRepository repository;

  public AvailableSlotDTO insertSlot(CreateAvailableSlotDTO dto) {
    Professional professional = authService.getAuthenticatedProfessional();

    validateSlotOverlap(professional, dto.getStartTime(), dto.getEndTime());

    AvailableSlot entity = new AvailableSlot();
    copyDtoToEntity(dto, entity, professional);
    entity = repository.save(entity);

    return new AvailableSlotDTO(entity);
  }

  private void validateSlotOverlap(Professional professional, LocalDateTime startTime, LocalDateTime endTime) {
    List<AvailableSlot> existingSlots = repository.findByProfessionalAndIsBookedFalse(professional);

    for (AvailableSlot slot : existingSlots) {
      boolean overlaps = (startTime.isBefore(slot.getEndTime()) && endTime.isAfter(slot.getStartTime()));

      if (overlaps) {
        throw new DatabaseException("Esse horário conflita com outro horário disponível já cadastrado.");
      }
    }
  }

  private void copyDtoToEntity(CreateAvailableSlotDTO dto, AvailableSlot entity, Professional professional) {
    entity.setProfessional(professional);
    entity.setStartTime(dto.getStartTime());
    entity.setEndTime(dto.getEndTime());
    entity.setBooked(false);
  }
}
