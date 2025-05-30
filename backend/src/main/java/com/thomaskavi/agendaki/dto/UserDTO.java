package com.thomaskavi.agendaki.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.thomaskavi.agendaki.entities.User;

public class UserDTO {

  private Long id;
  private String name;
  private String email;
  private String phone;
  private LocalDate birthDate;
  private List<String> roles = new ArrayList<>();

  public UserDTO() {
  }

  // Construtor para mapear de uma entidade User
  public UserDTO(User entity) {
    this.id = entity.getId();
    this.name = entity.getName();
    this.email = entity.getEmail();
    this.phone = entity.getPhone();
    this.birthDate = entity.getBirthDate();
    this.roles = entity.getRoles().stream()
        .map(role -> role.getAuthority())
        .collect(Collectors.toList());
  }

  // --- Getters ---
  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getEmail() {
    return email;
  }

  public String getPhone() {
    return phone;
  }

  public LocalDate getBirthDate() {
    return birthDate;
  }

  public List<String> getRoles() {
    return roles;
  }

  // --- Setters (se necessário) ---
  public void setId(Long id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public void setBirthDate(LocalDate birthDate) {
    this.birthDate = birthDate;
  }

  public void setRoles(List<String> roles) {
    this.roles = roles;
  }
}