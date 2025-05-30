package com.thomaskavi.agendaki.dto;

import java.time.LocalDate;

import com.thomaskavi.agendaki.entities.User;

import jakarta.validation.constraints.Past;

public class UserUpdateDTO {

  private String name;
  private String phone;
  @Past(message = "Verifique a data de nascimento e tente novamente")
  private LocalDate birthDate;

  public UserUpdateDTO() {
  }

  public UserUpdateDTO(User entity) {
    name = entity.getName();
    phone = entity.getPhone();
    birthDate = entity.getBirthDate();
  }

  public String getName() {
    return name;
  }

  public String getPhone() {
    return phone;
  }

  public LocalDate getBirthDate() {
    return birthDate;
  }

}
