package com.thomaskavi.agendaki.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.thomaskavi.agendaki.dto.ProfessionalDTO;
import com.thomaskavi.agendaki.entities.Professional;
import com.thomaskavi.agendaki.repository.ProfessionalRepository;
import com.thomaskavi.agendaki.services.exceptions.DatabaseException;
import com.thomaskavi.agendaki.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ProfessionalService {

  @Autowired
  private ProfessionalRepository repository;

  @Transactional(readOnly = true)
  public List<ProfessionalDTO> findAll() {
    List<Professional> result = repository.findAll();
    return result.stream().map(x -> new ProfessionalDTO(x)).toList();
  }

  @Transactional(readOnly = true)
  public ProfessionalDTO findById(Long id) {
    Professional professional = repository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Profissional não encontrado"));
    return new ProfessionalDTO(professional);
  }

  @Transactional
  public ProfessionalDTO insert(ProfessionalDTO dto) {
    Professional entity = new Professional();
    copyDtoToEntity(dto, entity);
    entity = repository.save(entity);
    return new ProfessionalDTO(entity);
  }

  @Transactional
  public ProfessionalDTO update(Long id, ProfessionalDTO dto) {
    try {
      Professional entity = repository.getReferenceById(id);
      copyDtoToEntity(dto, entity);
      entity = repository.save(entity);
      return new ProfessionalDTO(entity);
    } catch (EntityNotFoundException e) {
      throw new ResourceNotFoundException("Profissional não encontrado");
    }
  }

  @Transactional(propagation = Propagation.SUPPORTS)
  public void delete(Long id) {
    if (!repository.existsById(id)) {
      throw new ResourceNotFoundException("Profissional não encontrado");
    }
    try {
      repository.deleteById(id);
    } catch (DataIntegrityViolationException e) {
      throw new DatabaseException("Não é possível remover um profissional com agendamento(s) em aberto", e);
    }
  }

  private void copyDtoToEntity(ProfessionalDTO dto, Professional entity) {
    entity.setName(dto.getName());
    entity.setEmail(dto.getEmail());
    entity.setPassword(dto.getPassword());
    entity.setSlug(dto.getSlug());
    entity.setProfession(dto.getProfession());
    entity.setPhone(dto.getPhone());
    entity.setProfileImageUrl(dto.getProfileImageUrl());
    // Não copia serviços nem clientes aqui (associação controlada por endpoints
    // específicos)
  }

}
