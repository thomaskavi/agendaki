package com.thomaskavi.agendaki.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thomaskavi.agendaki.dto.ProfessionalDTO;
import com.thomaskavi.agendaki.dto.ProfessionalSignupDTO;
import com.thomaskavi.agendaki.dto.UserDTO;
import com.thomaskavi.agendaki.dto.UserSignupDTO;
import com.thomaskavi.agendaki.services.ProfessionalService;
import com.thomaskavi.agendaki.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/public")
public class PublicSignupController {

  @Autowired
  private UserService userService;

  @Autowired
  private ProfessionalService professionalService;

  @PostMapping("/client-signup")
  public ResponseEntity<UserDTO> signupUser(@RequestBody @Valid UserSignupDTO dto) {
    UserDTO user = userService.createUser(dto);
    return ResponseEntity.status(HttpStatus.CREATED).body(user);
  }

  @PostMapping("/professional-signup")
  public ResponseEntity<ProfessionalDTO> signupProfessional(@RequestBody @Valid ProfessionalSignupDTO dto) {
    ProfessionalDTO professional = professionalService.createProfessional(dto);
    return ResponseEntity.status(HttpStatus.CREATED).body(professional);
  }
}
