package com.thomaskavi.agendaki.dto;

import com.thomaskavi.agendaki.entities.Professional;

import lombok.Getter;

@Getter
public class ProfessionalDetailsDTO {

  private final Long id;
  private final String name;
  private final String email;
  private final String phone;
  private final String profileImageUrl;
  private final String slug;
  private final String profession;

  public ProfessionalDetailsDTO(Professional entity) {
    this.id = entity.getId();
    this.name = entity.getName();
    this.email = entity.getEmail();
    this.phone = entity.getPhone();
    this.profileImageUrl = entity.getProfileImageUrl();
    this.slug = entity.getSlug();
    this.profession = entity.getProfession();
  }

}
