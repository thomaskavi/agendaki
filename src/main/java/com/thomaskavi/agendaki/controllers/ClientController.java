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

import com.thomaskavi.agendaki.dto.ClientDTO;
import com.thomaskavi.agendaki.services.ClientService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/clients")
public class ClientController {

  @Autowired
  private ClientService service;

  @PreAuthorize("hasAnyRole('ROLE_PROFESSIONAL', 'ROLE_ADMIN')")
  @GetMapping
  public ResponseEntity<List<ClientDTO>> findAll() {
    List<ClientDTO> list = service.findAll();
    return ResponseEntity.ok(list);
  }

  @PreAuthorize("hasAnyRole('ROLE_PROFESSIONAL', 'ROLE_ADMIN')")
  @GetMapping(value = "/{id}")
  public ResponseEntity<ClientDTO> findById(@PathVariable Long id) {
    ClientDTO dto = service.findById(id);
    return ResponseEntity.ok(dto);
  }

  @PostMapping
  public ResponseEntity<ClientDTO> insert(@Valid @RequestBody ClientDTO dto) {
    dto = service.insert(dto);
    URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
        .buildAndExpand(dto.getId()).toUri();
    return ResponseEntity.created(uri).body(dto);
  }

  @PutMapping(value = "/{id}")
  public ResponseEntity<ClientDTO> update(@PathVariable Long id, @Valid @RequestBody ClientDTO dto) {
    dto = service.update(id, dto);
    return ResponseEntity.ok(dto);
  }

  @PreAuthorize("hasAnyRole('ROLE_PROFESSIONAL', 'ROLE_ADMIN')")
  @DeleteMapping(value = "/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    service.delete(id);
    return ResponseEntity.noContent().build();
  }
}
