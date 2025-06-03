package com.thomaskavi.agendaki.dto;

import com.thomaskavi.agendaki.entities.Professional;

import lombok.Getter;

@Getter
public class ProfessionalDTO extends UserDTO {

  private final String slug;
  private final String profession;
  private final String profileImageUrl;

  public ProfessionalDTO(Professional entity) {
    super(entity);
    this.slug = entity.getSlug();
    this.profession = entity.getProfession();
    this.profileImageUrl = entity.getProfileImageUrl();
  }

}
