package com.thomaskavi.agendaki.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.thomaskavi.agendaki.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

  Optional<Role> findByAuthority(String authority);
}
