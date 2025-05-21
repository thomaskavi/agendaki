package com.thomaskavi.agendaki.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;

public class UpdateAppointmentDTO {

  @NotNull(message = "Data e hora são obrigatórios")
  private LocalDateTime dateTime;

  @NotNull(message = "O ID do serviço é obrigatório")
  private Long serviceOfferedId;

  public UpdateAppointmentDTO() {
  }

  public LocalDateTime getDateTime() {
    return dateTime;
  }

  public void setDateTime(LocalDateTime dateTime) {
    this.dateTime = dateTime;
  }

  public Long getServiceOfferedId() {
    return serviceOfferedId;
  }

  public void setServiceOfferedId(Long serviceOfferedId) {
    this.serviceOfferedId = serviceOfferedId;
  }
}
