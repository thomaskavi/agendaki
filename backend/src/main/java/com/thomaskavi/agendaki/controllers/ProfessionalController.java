package com.thomaskavi.agendaki.controllers;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.thomaskavi.agendaki.dto.ProfessionalDTO;
import com.thomaskavi.agendaki.dto.ProfessionalDetailsDTO;
import com.thomaskavi.agendaki.dto.ProfessionalPublicDTO;
import com.thomaskavi.agendaki.dto.ProfessionalSignupDTO;
import com.thomaskavi.agendaki.dto.ProfessionalUpdateDTO;
import com.thomaskavi.agendaki.dto.UriDTO;
import com.thomaskavi.agendaki.services.ProfessionalService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/professionals")
public class ProfessionalController {

  @Autowired
  private ProfessionalService service;

  // Ex: GET /professionals?page=0&size=5&sort=name,asc
  @GetMapping
  public ResponseEntity<Page<ProfessionalDetailsDTO>> findAll(Pageable pageable) {
    Page<ProfessionalDetailsDTO> page = service.findAll(pageable);
    return ResponseEntity.ok(page);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ProfessionalDetailsDTO> findById(@PathVariable Long id) {
    ProfessionalDetailsDTO dto = service.findById(id);
    return ResponseEntity.ok(dto);
  }

  // Inserção pode ser permitida para admins
  @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
  @PostMapping("/admin")
  public ResponseEntity<ProfessionalDTO> adminInsert(@Valid @RequestBody ProfessionalSignupDTO dto) {
    ProfessionalDTO professional = service.adminInsert(dto);
    URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
        .buildAndExpand(professional.getId()).toUri();
    return ResponseEntity.created(uri).body(professional);
  }

  @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_PROFESSIONAL')")
  @PutMapping(value = "/{id}")
  public ResponseEntity<ProfessionalUpdateDTO> update(@PathVariable Long id,
      @Valid @RequestBody ProfessionalUpdateDTO dto) {
    ProfessionalUpdateDTO updated = service.update(id, dto);
    return ResponseEntity.ok(updated);
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @DeleteMapping(value = "/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    service.delete(id);
    return ResponseEntity.noContent().build();
  }

  @PreAuthorize("isAuthenticated()")
  @GetMapping("/agdk/{slug}")
  public ResponseEntity<ProfessionalPublicDTO> findBySlug(@PathVariable String slug) {
    ProfessionalPublicDTO dto = service.findBySlug(slug);
    return ResponseEntity.ok(dto);
  }

  @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_PROFESSIONAL')")
  @PostMapping(value = "/send-image")
  public ResponseEntity<UriDTO> uploadImage(@RequestParam("file") MultipartFile file) {
    UriDTO dto = service.uploadProfileImage(file);
    return ResponseEntity.ok().body(dto);
  }

}
