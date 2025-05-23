package com.thomaskavi.agendaki.dto;

import java.time.LocalDateTime;

import com.thomaskavi.agendaki.entities.Appointment;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AppointmentDTO {

  private Long id;

  @FutureOrPresent(message = "Não é possível agendar uma data passada")
  @NotNull(message = "Data e hora são obrigatórios")
  private LocalDateTime dateTime;

  @NotNull(message = "O ID do cliente é obrigatório")
  private Long clientId;

  private String clientName;
  private String clientEmail;
  private String clientPhone;

  @NotNull(message = "O ID do serviço é obrigatório")
  private Long serviceOfferedId;

  private Long professionalId;
  private String professionalName;
  private String serviceOfferedName;

  public AppointmentDTO() {
  }

  public AppointmentDTO(Appointment entity) {
    this.id = entity.getId();
    this.dateTime = entity.getDateTime();
    this.clientId = entity.getClient() != null ? entity.getClient().getId() : null;

    if (entity.getClient() != null) {
      this.clientName = entity.getClient().getName();
      this.clientEmail = entity.getClient().getEmail();
      this.clientPhone = entity.getClient().getPhone();
    }

    this.serviceOfferedId = entity.getService() != null ? entity.getService().getId() : null;

    if (entity.getProfessional() != null) {
      this.professionalId = entity.getProfessional().getId();
      this.professionalName = entity.getProfessional().getName();
    }

    if (entity.getService() != null) {
      this.serviceOfferedName = entity.getService().getName();
    }
  }

}
