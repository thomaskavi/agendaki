package com.thomaskavi.agendaki.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thomaskavi.agendaki.dto.UserMeDTO;
import com.thomaskavi.agendaki.services.UserService;

@RestController
@RequestMapping(value = "/users")
public class UserController {

  @Autowired
  private UserService service;

  @PreAuthorize("isAuthenticated()")
  @GetMapping(value = "/me")
  public ResponseEntity<UserMeDTO> getMe() {
    UserMeDTO dto = service.getMe();
    return ResponseEntity.ok(dto);
  }
}
