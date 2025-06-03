package com.thomaskavi.agendaki.dto;

import java.time.LocalDateTime;

import com.thomaskavi.agendaki.entities.AvailableSlot;
import com.thomaskavi.agendaki.validation.EndTimeAfterStartTime;

import jakarta.validation.constraints.FutureOrPresent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@EndTimeAfterStartTime(message = "O fim do agendamento não pode ser antes ou igual ao seu início.")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateAvailableSlotDTO {

  @FutureOrPresent(message = "A data e hora de início não pode ser no passado.")
  private LocalDateTime startTime;

  private LocalDateTime endTime;

  private boolean isBooked;

  public CreateAvailableSlotDTO(AvailableSlot entity) {
    this.startTime = entity.getStartTime();
    this.endTime = entity.getEndTime();
    this.isBooked = entity.isBooked();
  }

}
