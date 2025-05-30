package com.thomaskavi.agendaki.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.thomaskavi.agendaki.entities.Professional;

public class ProfessionalPublicDTO {
  private Long id;
  private String name;
  private String email;
  private String phone;
  private String profileImageUrl;
  private String slug;
  private String profession;
  private List<ServiceOfferedDTO> services = new ArrayList<>();

  public ProfessionalPublicDTO() {
  }

  // Construtor baseado na entidade
  public ProfessionalPublicDTO(Professional entity) {
    this.id = entity.getId();
    this.name = entity.getName();
    this.email = entity.getEmail();
    this.phone = entity.getPhone();
    this.profileImageUrl = entity.getProfileImageUrl();
    this.slug = entity.getSlug();
    this.profession = entity.getProfession();

    if (entity.getServices() != null) {
      this.services = entity.getServices()
          .stream()
          .map(ServiceOfferedDTO::new)
          .collect(Collectors.toList());
    }
  }

  // Getters e Setters

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

  public String getProfileImageUrl() {
    return profileImageUrl;
  }

  public void setProfileImageUrl(String profileImageUrl) {
    this.profileImageUrl = profileImageUrl;
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

  public List<ServiceOfferedDTO> getServices() {
    return services;
  }

  public void setServices(List<ServiceOfferedDTO> services) {
    this.services = services;
  }
}
