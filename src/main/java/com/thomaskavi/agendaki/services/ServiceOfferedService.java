package com.thomaskavi.agendaki.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.thomaskavi.agendaki.dto.ServiceOfferedDTO;
import com.thomaskavi.agendaki.entities.Professional;
import com.thomaskavi.agendaki.entities.ServiceOffered;
import com.thomaskavi.agendaki.repository.ProfessionalRepository;
import com.thomaskavi.agendaki.repository.ServiceOfferedRepository;
import com.thomaskavi.agendaki.services.exceptions.DatabaseException;
import com.thomaskavi.agendaki.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ServiceOfferedService {

  @Autowired
  private ServiceOfferedRepository repository;

  @Autowired
  private ProfessionalRepository professionalRepository;

  @Transactional(readOnly = true)
  public List<ServiceOfferedDTO> findAll() {
    List<ServiceOffered> result = repository.findAll();
    return result.stream().map(x -> new ServiceOfferedDTO(x)).toList();
  }

  @Transactional(readOnly = true)
  public ServiceOfferedDTO findById(Long id) {
    ServiceOffered entity = repository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Serviço não encontrado"));
    return new ServiceOfferedDTO(entity);
  }

  @Transactional
  public ServiceOfferedDTO insert(ServiceOfferedDTO dto) {
    ServiceOffered entity = new ServiceOffered();
    copyDtoToEntity(dto, entity);
    entity = repository.save(entity);
    return new ServiceOfferedDTO(entity);
  }

  @Transactional
  public ServiceOfferedDTO update(Long id, ServiceOfferedDTO dto) {
    try {
      ServiceOffered entity = repository.getReferenceById(id);
      copyDtoToEntity(dto, entity);
      entity = repository.save(entity);
      return new ServiceOfferedDTO(entity);
    } catch (EntityNotFoundException e) {
      throw new ResourceNotFoundException("Serviço não encontrado");
    }
  }

  @Transactional(propagation = Propagation.SUPPORTS)
  public void delete(Long id) {
    if (!repository.existsById(id)) {
      throw new ResourceNotFoundException("Serviço não encontrado");
    }
    try {
      repository.deleteById(id);
    } catch (DataIntegrityViolationException e) {
      throw new DatabaseException("Não é possível remover serviço que possui agendamento aberto.");
    }
  }

  private void copyDtoToEntity(ServiceOfferedDTO dto, ServiceOffered entity) {
    entity.setName(dto.getName());
    entity.setPrice(dto.getPrice());

    Professional professional = professionalRepository.findById(dto.getProfessionalId())
        .orElseThrow(() -> new ResourceNotFoundException("Profissional não encontrado"));
    entity.setProfessional(professional);
  }

}
