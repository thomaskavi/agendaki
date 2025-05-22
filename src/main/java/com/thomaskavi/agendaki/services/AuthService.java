package com.thomaskavi.agendaki.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thomaskavi.agendaki.entities.User;
import com.thomaskavi.agendaki.services.exceptions.ForbiddenException;

@Service
public class AuthService {

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
}
