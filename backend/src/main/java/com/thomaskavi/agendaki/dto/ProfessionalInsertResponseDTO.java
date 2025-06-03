package com.thomaskavi.agendaki.dto;

import java.time.LocalDate;

import com.thomaskavi.agendaki.entities.Professional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfessionalInsertResponseDTO {

  private String name;
  private String email;
  private String phone;
  private LocalDate birthDate;
  private String slug;
  private String profession;

  public ProfessionalInsertResponseDTO(Professional entity) {
    this.name = entity.getName();
    this.email = entity.getEmail();
    this.phone = entity.getPhone();
    this.birthDate = entity.getBirthDate();
    this.slug = entity.getSlug();
    this.profession = entity.getProfession();
  }
}
