package com.thomaskavi.agendaki.dto;

import java.time.LocalDate;

import com.thomaskavi.agendaki.entities.Professional;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class ProfessionalInsertResponseDTO {

  private String name;
  private String email;
  private String phone;
  private LocalDate birthDate;
  private String slug;
  private String profession;
  private String profileImageUrl;

  public ProfessionalInsertResponseDTO() {
  }

  // Construtor baseado na entidade
  public ProfessionalInsertResponseDTO(Professional entity) {
    name = entity.getName();
    email = entity.getEmail();
    phone = entity.getPhone();
    birthDate = entity.getBirthDate();
    slug = entity.getSlug();
    profession = entity.getProfession();
    profileImageUrl = entity.getProfileImageUrl();
  }

}
