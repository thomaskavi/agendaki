package com.thomaskavi.agendaki.dto;

import java.time.LocalDate;

import com.thomaskavi.agendaki.entities.User;

import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateDTO {

  private String name;

  private String phone;

  @Past(message = "Verifique a data de nascimento e tente novamente")
  private LocalDate birthDate;

  public UserUpdateDTO(User entity) {
    this.name = entity.getName();
    this.phone = entity.getPhone();
    this.birthDate = entity.getBirthDate();
  }
}