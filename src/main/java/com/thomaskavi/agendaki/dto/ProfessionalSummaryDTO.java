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

  public ProfessionalSummaryDTO(Long id, String name, String email, String profession, String profileImageUrl) {
    this.id = id;
    this.name = name;
    this.email = email;
    this.profession = profession;
    this.profileImageUrl = profileImageUrl;
  }

  public ProfessionalSummaryDTO(Professional entity) {
    id = entity.getId();
    name = entity.getName();
    email = entity.getEmail();
    profession = entity.getProfession();
    profileImageUrl = entity.getProfileImageUrl();
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
