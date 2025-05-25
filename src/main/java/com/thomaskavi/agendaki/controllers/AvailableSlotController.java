package com.thomaskavi.agendaki.controllers;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.thomaskavi.agendaki.dto.AvailableSlotDTO;
import com.thomaskavi.agendaki.dto.CreateAvailableSlotDTO;
import com.thomaskavi.agendaki.services.AvailableSlotService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/available-slots")
public class AvailableSlotController {

  @Autowired
  private AvailableSlotService service;

  @PreAuthorize("hasRole('ROLE_PROFESSIONAL')")
  @PostMapping
  public ResponseEntity<AvailableSlotDTO> insert(@RequestBody @Valid CreateAvailableSlotDTO dto) {
    AvailableSlotDTO responseDTO = service.insertSlot(dto);
    URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
        .buildAndExpand(responseDTO.getId()).toUri();
    return ResponseEntity.created(uri).body(responseDTO);
  }
}
