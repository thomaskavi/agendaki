package com.thomaskavi.agendaki.dto;

import java.time.LocalDate;

import com.thomaskavi.agendaki.entities.Professional;
import com.thomaskavi.agendaki.entities.User;

import jakarta.validation.constraints.Past;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserMeDTO {

  private Long id;
  private String name;
  private String email;
  private String phone;

  @Past(message = "Verifique a data de nascimento e tente novamente")
  private LocalDate birthDate;

  private String slug;
  private String profession;
  private String profileImageUrl;

  public UserMeDTO(User entity) {
    this.id = entity.getId();
    this.name = entity.getName();
    this.email = entity.getEmail();
    this.phone = entity.getPhone();
    this.birthDate = entity.getBirthDate();

    if (entity instanceof Professional professional) {
      this.slug = professional.getSlug();
      this.profession = professional.getProfession();
      this.profileImageUrl = professional.getProfileImageUrl();
    }
  }
}