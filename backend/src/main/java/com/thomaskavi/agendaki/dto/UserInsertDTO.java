package com.thomaskavi.agendaki.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserInsertDTO {

  @NotBlank(message = "O nome é obrigatório")
  private String name;

  @NotBlank(message = "O e-mail é obrigatório")
  @Email(message = "E-mail inválido")
  private String email;

  @NotBlank(message = "A senha é obrigatória")
  private String password;

  private String phone;

  @Past(message = "Verifique a data de nascimento e tente novamente")
  private LocalDate birthDate;
}
