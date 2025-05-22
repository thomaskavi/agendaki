package com.thomaskavi.agendaki.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import com.thomaskavi.agendaki.entities.Client;
import com.thomaskavi.agendaki.entities.Professional;
import com.thomaskavi.agendaki.repository.ClientRepository;
import com.thomaskavi.agendaki.repository.ProfessionalRepository;

@Service
public class AuthenticatedUserService {

  @Autowired
  private ProfessionalRepository repository;

  @Autowired
  private ClientRepository clientRepository;

  public Professional getAuthenticatedUser() {
    try {
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      Jwt jwtPrincipal = (Jwt) authentication.getPrincipal();
      String username = jwtPrincipal.getClaim("username"); // ou "email"
      return repository.findByEmail(username).get();
    } catch (Exception e) {
      throw new UsernameNotFoundException("Email not found");
    }
  }

  public Client getAuthenticatedClient() {
    try {
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      Jwt jwtPrincipal = (Jwt) authentication.getPrincipal();
      String username = jwtPrincipal.getClaim("username"); // ou "email"
      return clientRepository.findByEmail(username).get();
    } catch (Exception e) {
      throw new UsernameNotFoundException("Email not found");
    }
  }
}
