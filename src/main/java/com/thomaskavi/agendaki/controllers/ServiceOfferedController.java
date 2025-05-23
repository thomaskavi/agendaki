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

import com.thomaskavi.agendaki.dto.ServiceOfferedDTO;
import com.thomaskavi.agendaki.services.ServiceOfferedService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/services")
public class ServiceOfferedController {

  @Autowired
  private ServiceOfferedService service;

  @PreAuthorize("isAuthenticated()")
  @GetMapping
  public ResponseEntity<List<ServiceOfferedDTO>> findAll() {
    List<ServiceOfferedDTO> list = service.findAll();
    return ResponseEntity.ok(list);
  }

  @PreAuthorize("isAuthenticated()")
  @GetMapping(value = "/{id}")
  public ResponseEntity<ServiceOfferedDTO> findById(@PathVariable Long id) {
    ServiceOfferedDTO dto = service.findById(id);
    return ResponseEntity.ok(dto);
  }

  @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_PROFESSIONAL')")
  @PostMapping
  public ResponseEntity<ServiceOfferedDTO> insert(@Valid @RequestBody ServiceOfferedDTO dto) {
    dto = service.insert(dto);
    URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
        .buildAndExpand(dto.getId()).toUri();
    return ResponseEntity.created(uri).body(dto);
  }

  @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_PROFESSIONAL')")
  @PutMapping(value = "/{id}")
  public ResponseEntity<ServiceOfferedDTO> update(@PathVariable Long id, @Valid @RequestBody ServiceOfferedDTO dto) {
    dto = service.update(id, dto);
    return ResponseEntity.ok(dto);
  }

  @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_PROFESSIONAL')")
  @DeleteMapping(value = "/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    service.delete(id);
    return ResponseEntity.noContent().build();
  }
}
