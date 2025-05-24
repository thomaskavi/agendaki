package com.thomaskavi.agendaki.dto;

import com.thomaskavi.agendaki.entities.Professional;

public class ProfessionalDTO extends UserDTO {

  private String password;
  private String slug;
  private String profession;
  private String profileImageUrl;

  public ProfessionalDTO() {
    super();
  }

  public ProfessionalDTO(Professional entity) {
    super(entity); // Pega id, name, email, phone, roles do User
    this.password = entity.getPassword();
    this.slug = entity.getSlug();
    this.profession = entity.getProfession();
    this.profileImageUrl = entity.getProfileImageUrl();
  }

  public String getPassword() {
    return password;
  }

  public String getSlug() {
    return slug;
  }

  public String getProfession() {
    return profession;
  }

  public String getProfileImageUrl() {
    return profileImageUrl;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setSlug(String slug) {
    this.slug = slug;
  }

  public void setProfession(String profession) {
    this.profession = profession;
  }

  public void setProfileImageUrl(String profileImageUrl) {
    this.profileImageUrl = profileImageUrl;
  }
}
