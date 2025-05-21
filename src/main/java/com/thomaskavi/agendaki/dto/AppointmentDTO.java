package com.thomaskavi.agendaki.dto;

import java.time.LocalDateTime;

import com.thomaskavi.agendaki.entities.Appointment;

import jakarta.validation.constraints.NotNull;

public class AppointmentDTO {

  private Long id;

  @NotNull(message = "Data e hora são obrigatórios")
  private LocalDateTime dateTime;

  @NotNull(message = "O ID do cliente é obrigatório")
  private Long clientId;

  @NotNull(message = "O ID do serviço é obrigatório")
  private Long serviceOfferedId;

  public AppointmentDTO() {
  }

  public AppointmentDTO(Appointment entity) {
    this.id = entity.getId();
    this.dateTime = entity.getDateTime();
    this.clientId = entity.getClient() != null ? entity.getClient().getId() : null;
    this.serviceOfferedId = entity.getService() != null ? entity.getService().getId() : null;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public LocalDateTime getDateTime() {
    return dateTime;
  }

  public void setDateTime(LocalDateTime dateTime) {
    this.dateTime = dateTime;
  }

  public Long getClientId() {
    return clientId;
  }

  public void setClientId(Long clientId) {
    this.clientId = clientId;
  }

  public Long getServiceOfferedId() {
    return serviceOfferedId;
  }

  public void setServiceOfferedId(Long serviceOfferedId) {
    this.serviceOfferedId = serviceOfferedId;
  }

}
