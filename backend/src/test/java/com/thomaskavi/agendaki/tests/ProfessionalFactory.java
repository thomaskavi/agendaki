package com.thomaskavi.agendaki.tests;

import java.time.LocalDate;

import com.thomaskavi.agendaki.entities.Professional;

public class ProfessionalFactory {

  public static Professional createProfessional() {
    Professional professional = new Professional();
    professional.setId(1L);
    professional.setName("Maria da Silva");
    professional.setEmail("maria@gmail.com");
    professional.setPhone("11999999999");
    professional.setBirthDate(LocalDate.of(1990, 1, 1));
    professional.setSlug("maria-da-silva");
    professional.setProfession("Barbeira");
    professional.setProfileImageUrl("https://example.com/image.jpg");
    return professional;
  }

  public static Professional createProfessional(Long id, String name, String email) {
    Professional professional = createProfessional();
    professional.setId(id);
    professional.setName(name);
    professional.setEmail(email);
    return professional;
  }
}
