package com.thomaskavi.agendaki.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.thomaskavi.agendaki.dto.ServiceOfferedDTO;
import com.thomaskavi.agendaki.dto.ServiceOfferedUpdateDTO;
import com.thomaskavi.agendaki.entities.Professional;
import com.thomaskavi.agendaki.entities.ServiceOffered;
import com.thomaskavi.agendaki.entities.User;
import com.thomaskavi.agendaki.repository.ServiceOfferedRepository;
import com.thomaskavi.agendaki.services.exceptions.DatabaseException;
import com.thomaskavi.agendaki.services.exceptions.ForbiddenException;
import com.thomaskavi.agendaki.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ServiceOfferedService {

  @Autowired
  private AuthService authService;

  @Autowired
  private ServiceOfferedRepository repository;

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
    User user = authService.getAuthenticatedUser();

    if (!(user instanceof Professional)) {
      throw new ForbiddenException("Apenas profissionais podem criar serviços.");
    }

    ServiceOffered entity = new ServiceOffered();
    copyDtoToEntity(dto, entity);
    entity.setProfessional((Professional) user);
    entity = repository.save(entity);
    return new ServiceOfferedDTO(entity);
  }

  @Transactional
  public ServiceOfferedUpdateDTO update(Long id, ServiceOfferedUpdateDTO dto) {
    User user = authService.getAuthenticatedProfessional();

    try {
      ServiceOffered entity = repository.getReferenceById(id);

      if (!entity.getProfessional().getId().equals(user.getId())) {
        throw new ForbiddenException("Você só pode atualizar seus próprios serviços");
      }

      copyDtoToEntity(dto, entity);
      entity = repository.save(entity);
      return new ServiceOfferedUpdateDTO(entity);
    } catch (EntityNotFoundException e) {
      throw new ResourceNotFoundException("Serviço não encontrado");
    }
  }

  @Transactional(propagation = Propagation.SUPPORTS)
  public void delete(Long id) {
    ServiceOffered entity = repository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Serviço não encontrado"));

    User user = authService.getAuthenticatedUser();

    // Se não for admin, valida se é dono do serviço
    if (!user.hasRole("ROLE_ADMIN")) {
      // Verifica se user é profissional e dono do serviço
      if (!(user instanceof Professional) || !entity.getProfessional().getId().equals(user.getId())) {
        throw new ForbiddenException("Apenas administradores ou o profissional dono podem deletar este serviço.");
      }
    }

    try {
      repository.deleteById(id);
    } catch (DataIntegrityViolationException e) {
      throw new DatabaseException("Não é possível remover serviço que possui agendamento aberto.");
    }
  }

  private void copyDtoToEntity(ServiceOfferedDTO dto, ServiceOffered entity) {
    entity.setName(dto.getName());
    entity.setDescription(dto.getDescription());
    entity.setPrice(dto.getPrice());
    entity.setDurationInMinutes(dto.getDurationInMinutes());
  }

  private void copyDtoToEntity(ServiceOfferedUpdateDTO dto, ServiceOffered entity) {
    if (dto.getName() != null) {
      entity.setName(dto.getName());
    }

    if (dto.getDescription() != null) {
      entity.setDescription(dto.getDescription());
    }

    if (dto.getPrice() != null) {
      entity.setPrice(dto.getPrice());
    }

    if (dto.getDurationInMinutes() != null) {
      entity.setDurationInMinutes(dto.getDurationInMinutes());
    }

  }

}
