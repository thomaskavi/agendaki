package com.thomaskavi.agendaki.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thomaskavi.agendaki.entities.Professional;
import com.thomaskavi.agendaki.services.exceptions.ForbiddenException;

@Service
public class AuthService {

  @Autowired
  private AuthenticatedUserService authenticatedUserService;

  public void validateSelfOrAdmin(long userId) {
    Professional me = authenticatedUserService.getAuthenticatedUser();

    if (!me.hasRole("ROLE_ADMIN") && !me.getId().equals(userId)) {
      throw new ForbiddenException("Acceso negado");
    }
  }
}
