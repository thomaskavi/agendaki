package com.thomaskavi.agendaki.entities;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "tb_professional")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Professional {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private String email;
  private String password;

  @Column(unique = true)
  private String slug;

  private String profession;
  private String phone;
  private String profileImageUrl;

  @OneToMany(mappedBy = "professional", cascade = CascadeType.ALL, orphanRemoval = true)
  @Setter(AccessLevel.NONE)
  @ToString.Exclude
  private List<ServiceOffered> services;

  @OneToMany(mappedBy = "professional", cascade = CascadeType.ALL, orphanRemoval = true)
  @Setter(AccessLevel.NONE)
  @ToString.Exclude
  private List<Client> clients;
}
