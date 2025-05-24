package com.thomaskavi.agendaki.dto;

import java.time.LocalDate;

import com.thomaskavi.agendaki.entities.Professional;

public class ProfessionalUpdateDTO {

  private String name;

  private String phone;
  private LocalDate birthDate;

  private String slug;

  private String profession;

  private String profileImageUrl;

  public ProfessionalUpdateDTO() {
  }

  public ProfessionalUpdateDTO(Professional entity) {
    name = entity.getName();
    phone = entity.getPhone();
    birthDate = entity.getBirthDate();
    slug = entity.getSlug();
    profession = entity.getProfession();
    profileImageUrl = entity.getProfileImageUrl();
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public LocalDate getBirthDate() {
    return birthDate;
  }

  public void setBirthDate(LocalDate birthDate) {
    this.birthDate = birthDate;
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

  public String getProfileImageUrl() {
    return profileImageUrl;
  }

  public void setProfileImageUrl(String profileImageUrl) {
    this.profileImageUrl = profileImageUrl;
  }
}
