package com.thomaskavi.agendaki.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserSignupDTO {

  @NotBlank(message = "Nome é obrigatório")
  @Size(min = 3, max = 80, message = "Nome deve ter entre 3 e 80 caracteres")
  private String name;

  @NotBlank(message = "Email é obrigatório")
  @Email(message = "Email inválido")
  private String email;

  @NotBlank(message = "Senha é obrigatória")
  @Size(min = 6, message = "Senha deve ter no mínimo 6 caracteres")
  private String password;

  @NotBlank(message = "Telefone é obrigatório")
  private String phone;

  @Past(message = "Informe uma data válida")
  private LocalDate birthDate;
}