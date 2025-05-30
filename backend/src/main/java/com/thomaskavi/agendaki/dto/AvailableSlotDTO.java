package com.thomaskavi.agendaki.dto;

import java.time.LocalDateTime;

import com.thomaskavi.agendaki.entities.AvailableSlot;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AvailableSlotDTO {

  private Long id;
  private Long professionalId;
  private String professionalName;
  private LocalDateTime startTime;
  private LocalDateTime endTime;
  private boolean isBooked;

  public AvailableSlotDTO(AvailableSlot entity) {
    this.id = entity.getId();
    this.professionalId = entity.getProfessional().getId();
    this.professionalName = entity.getProfessional().getName();
    this.startTime = entity.getStartTime();
    this.endTime = entity.getEndTime();
    this.isBooked = entity.isBooked();
  }
}
