package com.thomaskavi.agendaki.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.thomaskavi.agendaki.dto.AppointmentDTO;
import com.thomaskavi.agendaki.dto.UpdateAppointmentDTO;
import com.thomaskavi.agendaki.entities.Appointment;
import com.thomaskavi.agendaki.entities.Professional;
import com.thomaskavi.agendaki.entities.ServiceOffered;
import com.thomaskavi.agendaki.entities.User;
import com.thomaskavi.agendaki.repository.AppointmentRepository;
import com.thomaskavi.agendaki.repository.ServiceOfferedRepository;
import com.thomaskavi.agendaki.repository.UserRepository;
import com.thomaskavi.agendaki.services.exceptions.DatabaseException;
import com.thomaskavi.agendaki.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class AppointmentService {

  @Autowired
  private AuthService authService;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private AppointmentRepository repository;

  @Autowired
  private ServiceOfferedRepository serviceOfferedRepository;

  @Transactional(readOnly = true)
  public List<AppointmentDTO> findAll() {
    List<Appointment> result = repository.findAll();
    return result.stream().map(x -> new AppointmentDTO(x)).toList();
  }

  @Transactional(readOnly = true)
  public AppointmentDTO findById(Long id) {
    Appointment entity = repository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Agendamento não encontrado"));
    return new AppointmentDTO(entity);
  }

  public AppointmentDTO insert(AppointmentDTO dto) {
    Appointment entity = new Appointment();
    copyDtoToEntity(dto, entity); // preenche client e service
    // pega o professional pelo service
    entity.setProfessional(entity.getService().getProfessional());
    entity = repository.save(entity);
    return new AppointmentDTO(entity); // agora inclui os campos extras no retorno
  }

  @Transactional
  public AppointmentDTO update(Long id, UpdateAppointmentDTO dto) {
    try {
      Appointment entity = repository.getReferenceById(id);
      entity.setDateTime(dto.getDateTime());
      ServiceOffered service = serviceOfferedRepository.findById(dto.getServiceOfferedId())
          .orElseThrow(() -> new ResourceNotFoundException("Serviço não encontrado"));
      entity.setService(service);
      entity = repository.save(entity);
      return new AppointmentDTO(entity);
    } catch (EntityNotFoundException e) {
      throw new ResourceNotFoundException("Agendamento não encontrado");
    }
  }

  @Transactional(propagation = Propagation.SUPPORTS)
  public void delete(Long id) {
    if (!repository.existsById(id)) {
      throw new ResourceNotFoundException("Agendamento não encontrado");
    }
    try {
      repository.deleteById(id);
    } catch (DataIntegrityViolationException e) {
      throw new DatabaseException("Falha de integridade referencial");
    }
  }

  private void copyDtoToEntity(AppointmentDTO dto, Appointment entity) {
    entity.setDateTime(dto.getDateTime());
    User client = userRepository.findById(dto.getClientId())
        .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado"));
    if (!client.hasRole("ROLE_CLIENT")) {
      throw new DatabaseException("Usuário informado não é um cliente válido");
    }
    entity.setClient(client);
    ServiceOffered service = serviceOfferedRepository.findById(dto.getServiceOfferedId())
        .orElseThrow(() -> new ResourceNotFoundException("Serviço não encontrado"));
    entity.setService(service);
  }

  @Transactional(readOnly = true)
  public List<AppointmentDTO> findByAuthenticatedUser() {
    User user = authService.getAuthenticatedUser();
    List<Appointment> list = repository.findByClient(user);
    return list.stream().map(AppointmentDTO::new).toList();
  }

  @Transactional(readOnly = true)
  public List<AppointmentDTO> findByProfessional() {
    Professional professional = authService.getAuthenticatedProfessional();
    List<Appointment> list = repository.findByProfessional(professional);
    return list.stream().map(AppointmentDTO::new).toList();
  }
}
