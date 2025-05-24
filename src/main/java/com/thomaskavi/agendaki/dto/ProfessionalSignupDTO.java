package com.thomaskavi.agendaki.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ProfessionalSignupDTO extends UserSignupDTO { // Estende UserSignupDTO

  @NotBlank(message = "Slug é obrigatório")
  @Size(min = 3, max = 50, message = "Slug deve ter entre 3 e 50 caracteres")
  private String slug;

  @NotBlank(message = "Profissão é obrigatória")
  private String profession;

  private String profileImageUrl; // Não @NotBlank se for opcional

  public ProfessionalSignupDTO() {
    super();
  }

  // --- Getters e Setters ---
  public String getSlug() {
    return slug;
  }

  public void setSlug(String slug) {
    this.slug = slug;
  }

  public String getProfession() {
    return profession;
  }

  public void setProfession(String profession) {
    this.profession = profession;
  }

  public String getProfileImageUrl() {
    return profileImageUrl;
  }

  public void setProfileImageUrl(String profileImageUrl) {
    this.profileImageUrl = profileImageUrl;
  }
}