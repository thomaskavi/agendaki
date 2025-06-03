package com.thomaskavi.agendaki.dto;

import com.thomaskavi.agendaki.entities.Professional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProfessionalSummaryDTO {

  private Long id;
  private String name;
  private String email;
  private String profession;

  public ProfessionalSummaryDTO(Professional entity) {
    this.id = entity.getId();
    this.name = entity.getName();
    this.email = entity.getEmail();
    this.profession = entity.getProfession();
  }
}
