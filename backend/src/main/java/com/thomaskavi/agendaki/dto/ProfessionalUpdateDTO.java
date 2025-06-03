package com.thomaskavi.agendaki.dto;

import java.time.LocalDate;

import com.thomaskavi.agendaki.entities.Professional;

import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfessionalUpdateDTO {

  private String name;
  private String phone;

  @Past(message = "Verifique a data de nascimento e tente novamente")
  private LocalDate birthDate;

  private String slug;

  private String profession;

  private String profileImageUrl;

  public ProfessionalUpdateDTO(Professional entity) {
    this.name = entity.getName();
    this.phone = entity.getPhone();
    this.birthDate = entity.getBirthDate();
    this.slug = entity.getSlug();
    this.profession = entity.getProfession();
    this.profileImageUrl = entity.getProfileImageUrl();
  }
}