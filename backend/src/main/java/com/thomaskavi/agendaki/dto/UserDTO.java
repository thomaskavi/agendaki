package com.thomaskavi.agendaki.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.thomaskavi.agendaki.entities.User;

import jakarta.validation.constraints.Past;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO {

  private Long id;
  private String name;
  private String email;
  private String phone;

  @Past(message = "Verifique a data de nascimento e tente novamente")
  private LocalDate birthDate;

  private List<String> roles = new ArrayList<>();

  public UserDTO(User entity) {
    this.id = entity.getId();
    this.name = entity.getName();
    this.email = entity.getEmail();
    this.phone = entity.getPhone();
    this.birthDate = entity.getBirthDate();
    this.roles = entity.getRoles()
        .stream()
        .map(role -> role.getAuthority())
        .collect(Collectors.toList());
  }
}
