package com.thomaskavi.agendaki.dto;

import com.thomaskavi.agendaki.entities.Professional;

public class ProfessionalDetailsDTO {

  private Long id;
  private String name;
  private String email;
  private String phone;
  private String profileImageUrl;
  private String slug;
  private String profession;

  public ProfessionalDetailsDTO(Professional entity) {
    this.id = entity.getId();
    this.name = entity.getName();
    this.email = entity.getEmail();
    this.phone = entity.getPhone();
    this.profileImageUrl = entity.getProfileImageUrl();
    this.slug = entity.getSlug();
    this.profession = entity.getProfession();
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

  public String getPhone() {
    return phone;
  }

  public String getProfileImageUrl() {
    return profileImageUrl;
  }

  public String getSlug() {
    return slug;
  }

  public String getProfession() {
    return profession;
  }

}
