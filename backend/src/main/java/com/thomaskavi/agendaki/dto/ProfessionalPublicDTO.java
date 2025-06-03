package com.thomaskavi.agendaki.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.thomaskavi.agendaki.entities.Professional;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProfessionalPublicDTO {

  private Long id;
  private String name;
  private String email;
  private String phone;
  private String profileImageUrl;
  private String slug;
  private String profession;

  private List<ServiceOfferedDTO> services = new ArrayList<>();

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
}