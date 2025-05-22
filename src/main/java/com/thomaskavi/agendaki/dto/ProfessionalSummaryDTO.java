package com.thomaskavi.agendaki.dto;

import com.thomaskavi.agendaki.entities.Professional;

public class ProfessionalSummaryDTO {

  private Long id;
  private String name;
  private String email;
  private String profession;
  private String profileImageUrl;

  public ProfessionalSummaryDTO() {
  }

  public ProfessionalSummaryDTO(Professional entity) {
    this.id = entity.getId();
    this.name = entity.getName();
    this.email = entity.getEmail();
    this.profession = entity.getProfession();
    this.profileImageUrl = entity.getProfileImageUrl();
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getEmail() {
    return email;
  }

  public String getProfession() {
    return profession;
  }

  public String getProfileImageUrl() {
    return profileImageUrl;
  }
}
