package com.thomaskavi.agendaki.entities;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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
public class Professional implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  @Column(unique = true)
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

  @ManyToMany
  @JoinTable(name = "tb_professional_role", joinColumns = @JoinColumn(name = "professional_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
  private Set<Role> roles = new HashSet<>();

  public void addRole(Role role) {
    roles.add(role);
  }

  public boolean hasRole(String roleName) {
    for (Role role : roles) {
      if (role.getAuthority().equals(roleName)) {
        return true;
      }
    }
    return false;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return roles;
  }

  @Override
  public String getUsername() {
    return email;
  }
}
