package com.thomaskavi.agendaki.dto;

import com.thomaskavi.agendaki.entities.ServiceOffered;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public class ServiceOfferedDTO {

  private Long id;

  @NotBlank(message = "O nome do serviço é obrigatório")
  @Size(min = 5, max = 50, message = "Nome do serviço deve ter entre 5 e 50 caracteres")
  private String name;

  @NotBlank(message = "Informe uma breve descrição do seu serviço")
  @Size(min = 10, max = 100, message = "Descrição deve ter entre 10 e 100 caracteres")
  private String description;

  @NotNull(message = "O preço é obrigatório")
  @PositiveOrZero
  private Double price;

  private Integer durationInMinutes;

  public ServiceOfferedDTO() {
  }

  public ServiceOfferedDTO(ServiceOffered entity) {
    this.id = entity.getId();
    this.name = entity.getName();
    this.description = entity.getDescription();
    this.price = entity.getPrice();
    this.durationInMinutes = entity.getDurationInMinutes();
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

  public Integer getDurationInMinutes() {
    return durationInMinutes;
  }

  public void setDurationInMinutes(Integer durationInMinutes) {
    this.durationInMinutes = durationInMinutes;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

}
