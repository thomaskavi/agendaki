package com.thomaskavi.agendaki.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.thomaskavi.agendaki.dto.AppointmentDTO;
import com.thomaskavi.agendaki.dto.UpdateAppointmentDTO;
import com.thomaskavi.agendaki.services.AppointmentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/appointments")
public class AppointmentController {

  @Autowired
  private AppointmentService service;

  @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
  @GetMapping
  public ResponseEntity<List<AppointmentDTO>> findAll() {
    List<AppointmentDTO> list = service.findAll();
    return ResponseEntity.ok(list);
  }

  @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
  @GetMapping(value = "/{id}")
  public ResponseEntity<AppointmentDTO> findById(@PathVariable Long id) {
    AppointmentDTO dto = service.findById(id);
    return ResponseEntity.ok(dto);
  }

  @PreAuthorize("isAuthenticated()")
  @PostMapping
  public ResponseEntity<AppointmentDTO> insert(@Valid @RequestBody AppointmentDTO dto) {
    dto = service.insert(dto);
    URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
        .buildAndExpand(dto.getId()).toUri();
    return ResponseEntity.created(uri).body(dto);
  }

  @PreAuthorize("isAuthenticated()")
  @PutMapping(value = "/{id}")
  public ResponseEntity<AppointmentDTO> update(@PathVariable Long id, @Valid @RequestBody UpdateAppointmentDTO dto) {
    AppointmentDTO updatedDto = service.update(id, dto);
    return ResponseEntity.ok(updatedDto);
  }

  @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
  @DeleteMapping(value = "/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    service.delete(id);
    return ResponseEntity.noContent().build();
  } 

  @PreAuthorize("isAuthenticated()")
  @GetMapping("/my")
  public ResponseEntity<List<AppointmentDTO>> findMyAppointments() {
    List<AppointmentDTO> list = service.findByAuthenticatedUser();
    return ResponseEntity.ok(list);
  }

  @PreAuthorize("hasRole('ROLE_PROFESSIONAL')")
  @GetMapping("/received")
  public ResponseEntity<List<AppointmentDTO>> findReceivedAppointments() {
    List<AppointmentDTO> list = service.findByProfessional();
    return ResponseEntity.ok(list);
  }

  @PreAuthorize("hasRole('ROLE_PROFESSIONAL')")
  @PatchMapping("/{id}/accept")
  public ResponseEntity<Void> acceptAppointment(@PathVariable Long id) {
    service.acceptAppointment(id);
    return ResponseEntity.ok().build();
  }

  @PreAuthorize("hasRole('ROLE_PROFESSIONAL')")
  @PatchMapping("/{id}/complete")
  public ResponseEntity<Void> completeAppointment(@PathVariable Long id) {
    service.completeAppointment(id);
    return ResponseEntity.ok().build();
  }

  @PreAuthorize("isAuthenticated()")
  @PatchMapping("/{id}/cancel")
  public ResponseEntity<Void> cancelAppointment(@PathVariable Long id) {
    service.cancelAppointment(id);
    return ResponseEntity.ok().build();
  }
}
