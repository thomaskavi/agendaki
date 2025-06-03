package com.thomaskavi.agendaki.dto;

import java.time.LocalDateTime;

import com.thomaskavi.agendaki.entities.AvailableSlot;

import lombok.Getter;

@Getter
public class AvailableSlotDTO {

  private final Long id;
  private final Long professionalId;
  private final String professionalName;
  private final LocalDateTime startTime;
  private final LocalDateTime endTime;
  private final boolean isBooked;

  public AvailableSlotDTO(AvailableSlot entity) {
    this.id = entity.getId();
    this.professionalId = entity.getProfessional().getId();
    this.professionalName = entity.getProfessional().getName();
    this.startTime = entity.getStartTime();
    this.endTime = entity.getEndTime();
    this.isBooked = entity.isBooked();
  }

}
