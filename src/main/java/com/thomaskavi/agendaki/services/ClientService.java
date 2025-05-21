package com.thomaskavi.agendaki.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.thomaskavi.agendaki.dto.ClientDTO;
import com.thomaskavi.agendaki.entities.Client;
import com.thomaskavi.agendaki.entities.Professional;
import com.thomaskavi.agendaki.repository.ClientRepository;
import com.thomaskavi.agendaki.repository.ProfessionalRepository;
import com.thomaskavi.agendaki.services.exceptions.DatabaseException;
import com.thomaskavi.agendaki.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ClientService {

  @Autowired
  private ClientRepository repository;

  @Autowired
  private ProfessionalRepository professionalRepository;

  @Transactional(readOnly = true)
  public List<ClientDTO> findAll() {
    List<Client> result = repository.findAll();
    return result.stream().map(x -> new ClientDTO(x)).toList();
  }

  @Transactional(readOnly = true)
  public ClientDTO findById(Long id) {
    Client client = repository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado"));
    return new ClientDTO(client);
  }

  @Transactional
  public ClientDTO insert(ClientDTO dto) {
    Client entity = new Client();
    copyDtoToEntity(dto, entity);
    entity = repository.save(entity);
    return new ClientDTO(entity);
  }

  @Transactional
  public ClientDTO update(Long id, ClientDTO dto) {
    try {
      Client entity = repository.getReferenceById(id);
      copyDtoToEntity(dto, entity);
      entity = repository.save(entity);
      return new ClientDTO(entity);
    } catch (EntityNotFoundException e) {
      throw new ResourceNotFoundException("Cliente não encontrado");
    }
  }

  @Transactional(propagation = Propagation.SUPPORTS)
  public void delete(Long id) {
    if (!repository.existsById(id)) {
      throw new ResourceNotFoundException("Cliente não encontrado");
    }
    try {
      repository.deleteById(id);
    } catch (DataIntegrityViolationException e) {
      throw new DatabaseException("Não é possível remover um cliente com um agendamento aberto.", e);
    }
  }

  private void copyDtoToEntity(ClientDTO dto, Client entity) {
    entity.setName(dto.getName());
    entity.setEmail(dto.getEmail());
    entity.setPhone(dto.getPhone());

    if (dto.getProfessionalId() != null) {
      Professional professional = professionalRepository.findById(dto.getProfessionalId())
          .orElseThrow(() -> new ResourceNotFoundException("Profissional vinculado não encontrado"));
      entity.setProfessional(professional);
    } else {
      entity.setProfessional(null);
    }
  }

}
