package com.thomaskavi.agendaki.dto;

import com.thomaskavi.agendaki.entities.ServiceOffered;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ServiceOfferedDTO {

  private Long id;

  @NotBlank(message = "O nome do serviço é obrigatório")
  private String name;

  @NotNull(message = "O preço é obrigatório")
  private Double price;

  @NotNull(message = "O ID do profissional é obrigatório")
  private Long professionalId;

  public ServiceOfferedDTO() {
  }

  public ServiceOfferedDTO(ServiceOffered entity) {
    this.id = entity.getId();
    this.name = entity.getName();
    this.price = entity.getPrice();
    this.professionalId = entity.getProfessional() != null ? entity.getProfessional().getId() : null;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }

  public Long getProfessionalId() {
    return professionalId;
  }

  public void setProfessionalId(Long professionalId) {
    this.professionalId = professionalId;
  }

}
