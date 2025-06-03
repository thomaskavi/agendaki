package com.thomaskavi.agendaki.dto;

import com.thomaskavi.agendaki.entities.ServiceOffered;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ServiceOfferedDTO {

  private Long id;

  @NotBlank(message = "O nome do serviço é obrigatório")
  @Size(min = 5, max = 50, message = "Nome do serviço deve ter entre 5 e 50 caracteres")
  private String name;

  @NotBlank(message = "Informe uma breve descrição do seu serviço")
  @Size(min = 10, max = 100, message = "Descrição deve ter entre 10 e 100 caracteres")
  private String description;

  @NotNull(message = "O preço é obrigatório")
  @PositiveOrZero(message = "Preço não pode ser negativo")
  private Double price;

  private Integer durationInMinutes;

  public ServiceOfferedDTO(ServiceOffered entity) {
    this.id = entity.getId();
    this.name = entity.getName();
    this.description = entity.getDescription();
    this.price = entity.getPrice();
    this.durationInMinutes = entity.getDurationInMinutes();
  }
}
