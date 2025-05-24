package com.thomaskavi.agendaki.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import com.thomaskavi.agendaki.entities.Professional;
import com.thomaskavi.agendaki.entities.User;

public class UserMeDTO {

  private Long id;
  private String name;
  private String email;
  private String phone;
  private LocalDate birthDate;
  private List<String> roles;

  private String slug;
  private String profession;
  private String profileImageUrl;

  public UserMeDTO(User entity) {
    this.id = entity.getId();
    this.name = entity.getName();
    this.email = entity.getEmail();
    this.phone = entity.getPhone();
    this.birthDate = entity.getBirthDate();
    this.roles = entity.getRoles().stream()
        .map(role -> role.getAuthority())
        .collect(Collectors.toList());

    // Se for um profissional, adiciona campos específicos
    if (entity instanceof Professional professional) {
      this.slug = professional.getSlug();
      this.profession = professional.getProfession();
      this.profileImageUrl = professional.getProfileImageUrl();
    }
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

  public List<String> getRoles() {
    return roles;
  }

  public void setRoles(List<String> roles) {
    this.roles = roles;
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
