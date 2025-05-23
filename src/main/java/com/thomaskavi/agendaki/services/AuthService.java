package com.thomaskavi.agendaki.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import com.thomaskavi.agendaki.entities.Professional;
import com.thomaskavi.agendaki.entities.User;
import com.thomaskavi.agendaki.repository.ProfessionalRepository;
import com.thomaskavi.agendaki.services.exceptions.ForbiddenException;
import com.thomaskavi.agendaki.services.exceptions.ResourceNotFoundException;

@Service
public class AuthService {

  @Autowired
  private ProfessionalRepository professionalRepository;

  @Autowired
  private UserService userService;

  public void validateAdmin() {
    User me = userService.authenticated();
    if (!me.hasRole("ROLE_ADMIN")) {
      throw new ForbiddenException("Acesso negado: somente administradores podem acessar este recurso");
    }
  }

  public void validateSelfOrAdmin(Long userId) {
    User me = userService.authenticated();
    if (me.hasRole("ROLE_ADMIN")) {
      return;
    }
    if (!me.getId().equals(userId)) {
      throw new ForbiddenException("Acesso negado, o usuário só pode acessar seus próprios dados");
    }
  }

  public Professional getAuthenticatedProfessional() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    Jwt jwt = (Jwt) authentication.getPrincipal();
    String email = jwt.getClaim("username");

    return professionalRepository.findByEmail(email)
        .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
  }

  public User getAuthenticatedUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication == null || !authentication.isAuthenticated()) {
      throw new ForbiddenException("Usuário não autenticado");
    }

    Jwt jwt = (Jwt) authentication.getPrincipal();
    String email = jwt.getClaim("username");

    return userService.findByEmail(email)
        .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
  }

}
