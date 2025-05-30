package com.thomaskavi.agendaki.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thomaskavi.agendaki.dto.UserMeDTO;
import com.thomaskavi.agendaki.dto.UserUpdateDTO;
import com.thomaskavi.agendaki.services.UserService;

import jakarta.validation.Valid;

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

  @PreAuthorize("isAuthenticated()")
  @PutMapping("/update-me")
  public ResponseEntity<UserUpdateDTO> updateUser(@Valid @RequestBody UserUpdateDTO dto) {
    UserUpdateDTO updatedUser = service.updateUser(dto);
    return ResponseEntity.ok(updatedUser);
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @DeleteMapping(value = "/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    service.delete(id);
    return ResponseEntity.noContent().build();
  }
}
