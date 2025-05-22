package com.thomaskavi.agendaki.dto;

import com.thomaskavi.agendaki.entities.Professional;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ProfessionalDTO extends UserDTO {

  @NotBlank(message = "Senha é obrigatória")
  @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres")
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
