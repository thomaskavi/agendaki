package com.thomaskavi.agendaki.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateAppointmentDTO {

  @NotNull(message = "Horário de início é obrigatório")
  @FutureOrPresent(message = "A data e hora de início não pode ser no passado")
  private LocalDateTime startTime;

}
