package com.thomaskavi.agendaki.dto;

import com.thomaskavi.agendaki.entities.Professional;

public class ProfessionalDTO extends UserDTO {

  private String slug;
  private String profession;
  private String profileImageUrl;

  public ProfessionalDTO() {
    super();
  }

  // Construtor para mapear de uma entidade Professional
  public ProfessionalDTO(Professional entity) {
    super(entity); // Chama o construtor de UserDTO(User entity) para mapear os campos de User
    this.slug = entity.getSlug();
    this.profession = entity.getProfession();
    this.profileImageUrl = entity.getProfileImageUrl();
  }

  // --- Getters ---
  public String getSlug() {
    return slug;
  }

  public String getProfession() {
    return profession;
  }

  public String getProfileImageUrl() {
    return profileImageUrl;
  }

  // --- Setters (se necessário) ---
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