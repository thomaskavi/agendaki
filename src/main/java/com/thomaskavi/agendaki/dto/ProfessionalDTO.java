package com.thomaskavi.agendaki.dto;

import com.thomaskavi.agendaki.entities.Professional;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ProfessionalDTO {
  private Long id;

  @NotBlank(message = "Nome é obrigatório")
  private String name;

  @Email(message = "Email inválido")
  @NotBlank(message = "Email é obrigatório")
  private String email;

  @NotBlank(message = "Senha é obrigatória")
  @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres")
  private String password;

  private String slug;
  private String profession;
  private String phone;
  private String profileImageUrl;

  public ProfessionalDTO() {
  }

  public ProfessionalDTO(Professional entity) {
    this.id = entity.getId();
    this.name = entity.getName();
    this.email = entity.getEmail();
    this.password = entity.getPassword();
    this.slug = entity.getSlug();
    this.profession = entity.getProfession();
    this.phone = entity.getPhone();
    this.profileImageUrl = entity.getProfileImageUrl();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

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

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getProfileImageUrl() {
    return profileImageUrl;
  }

  public void setProfileImageUrl(String profileImageUrl) {
    this.profileImageUrl = profileImageUrl;
  }
}