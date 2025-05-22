package com.thomaskavi.agendaki.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.thomaskavi.agendaki.dto.ProfessionalDTO;
import com.thomaskavi.agendaki.dto.ProfessionalDetailsDTO;
import com.thomaskavi.agendaki.services.ProfessionalService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/professionals")
public class ProfessionalController {

  @Autowired
  private ProfessionalService service;

  @PreAuthorize("isAuthenticated()")
  @GetMapping
  public ResponseEntity<List<ProfessionalDetailsDTO>> findAll() {
    List<ProfessionalDetailsDTO> list = service.findAll();
    return ResponseEntity.ok(list);
  }

  @PreAuthorize("isAuthenticated()")
  @GetMapping(value = "/{id}")
  public ResponseEntity<ProfessionalDetailsDTO> findById(@PathVariable Long id) {
    ProfessionalDetailsDTO dto = service.findById(id);
    return ResponseEntity.ok(dto);
  }

  // Inserção pode ser permitida para admins e profissionais
  @PreAuthorize("hasAnyRole('ADMIN', 'PROFESSIONAL')")
  @PostMapping
  public ResponseEntity<ProfessionalDTO> insert(@Valid @RequestBody ProfessionalDTO dto) {
    dto = service.insert(dto);
    URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
        .buildAndExpand(dto.getId()).toUri();
    return ResponseEntity.created(uri).body(dto);
  }

  // Update: validado via service para só profissional dono ou admin
  @PreAuthorize("hasAnyRole('ADMIN', 'PROFESSIONAL')")
  @PutMapping(value = "/{id}")
  public ResponseEntity<ProfessionalDTO> update(@PathVariable Long id, @Valid @RequestBody ProfessionalDTO dto) {
    dto = service.update(id, dto);
    return ResponseEntity.ok(dto);
  }

  // Delete: só admin pode deletar (validado no service)
  @PreAuthorize("hasRole('ADMIN')")
  @DeleteMapping(value = "/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    service.delete(id);
    return ResponseEntity.noContent().build();
  }
}
