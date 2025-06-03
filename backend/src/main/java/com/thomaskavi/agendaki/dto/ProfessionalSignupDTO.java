package com.thomaskavi.agendaki.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfessionalSignupDTO extends UserSignupDTO {

  @NotBlank(message = "Slug é obrigatório")
  @Size(min = 3, max = 25, message = "Slug deve ter entre 3 e 25 caracteres")
  private String slug;

  @NotBlank(message = "Profissão é obrigatória")
  private String profession;
}