package com.thomaskavi.agendaki.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.thomaskavi.agendaki.dto.LoginRequest;
import com.thomaskavi.agendaki.dto.ProfessionalInsertResponseDTO;
import com.thomaskavi.agendaki.dto.ProfessionalSignupDTO;
import com.thomaskavi.agendaki.dto.PublicUserDTO;
import com.thomaskavi.agendaki.dto.UserSignupDTO;
import com.thomaskavi.agendaki.services.ProfessionalService;
import com.thomaskavi.agendaki.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/public")
public class PublicSignupController {

  @Value("${security.client-id}")
  private String clientId;

  @Value("${security.client-secret}")
  private String clientSecret;

  @Value("${agendaki.auth-server-url}")
  private String authServerUrl;

  @Autowired
  private UserService userService;

  @Autowired
  private ProfessionalService professionalService;

  @PostMapping("/client-signup")
  public ResponseEntity<PublicUserDTO> signupUser(@RequestBody @Valid UserSignupDTO dto) {
    PublicUserDTO user = userService.createUser(dto);
    return ResponseEntity.status(HttpStatus.CREATED).body(user);
  }

  @PostMapping("/professional-signup")
  public ResponseEntity<ProfessionalInsertResponseDTO> signupProfessional(
      @RequestBody @Valid ProfessionalSignupDTO dto) {
    ProfessionalInsertResponseDTO professional = professionalService.createProfessional(dto);
    return ResponseEntity.status(HttpStatus.CREATED).body(professional);
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody LoginRequest request) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    headers.setBasicAuth(clientId, clientSecret);

    MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
    form.add("grant_type", "password");
    form.add("username", request.getUsername());
    form.add("password", request.getPassword());
    form.add("scope", "read");

    HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(form, headers);
    RestTemplate restTemplate = new RestTemplate();

    try {
      ResponseEntity<String> response = restTemplate.postForEntity(
          authServerUrl + "/oauth2/token", entity, String.class);
      return ResponseEntity.ok(response.getBody());
    } catch (HttpClientErrorException e) {
      return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
    }
  }
}
