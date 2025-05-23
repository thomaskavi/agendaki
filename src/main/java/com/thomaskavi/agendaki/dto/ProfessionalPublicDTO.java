package com.thomaskavi.agendaki.dto;

import com.thomaskavi.agendaki.entities.Professional;

public class ProfessionalPublicDTO {
  private Long id;
  private String name;
  private String slug;
  private String profession;

  public ProfessionalPublicDTO(Professional entity) {
    this.id = entity.getId();
    this.name = entity.getName();
    this.slug = entity.getSlug();
    this.profession = entity.getProfession();
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

}
