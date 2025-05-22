package com.thomaskavi.agendaki.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;

import com.thomaskavi.agendaki.entities.Client;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class ClientDTO {

  private Long id;

  @NotBlank(message = "Nome é obrigatório")
  private String name;

  @Column(unique = true)
  @Email(message = "Email inválido")
  @NotBlank(message = "Email é obrigatório")
  private String email;

  private String phone;

  private List<String> roles = new ArrayList<>();

  public ClientDTO() {
  }

  public ClientDTO(Client entity) {
    this.id = entity.getId();
    this.name = entity.getName();
    this.email = entity.getEmail();
    this.phone = entity.getPhone();
    for (GrantedAuthority role : entity.getRoles()) {
      roles.add(role.getAuthority());
    }
  }

  // Getters e setters

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

  public List<String> getRoles() {
    return roles;
  }
}
