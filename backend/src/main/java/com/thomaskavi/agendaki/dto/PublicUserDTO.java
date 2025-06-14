package com.thomaskavi.agendaki.dto;

import java.time.LocalDate;

import com.thomaskavi.agendaki.entities.User;

import jakarta.validation.constraints.Past;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PublicUserDTO {

  private String name;
  private String email;
  private String phone;

  @Past(message = "Verifique a data de nascimento e tente novamente")
  private LocalDate birthDate;

  public PublicUserDTO(User entity) {
    this.name = entity.getName();
    this.email = entity.getEmail();
    this.phone = entity.getPhone();
    this.birthDate = entity.getBirthDate();
  }
}