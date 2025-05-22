package com.thomaskavi.agendaki.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.thomaskavi.agendaki.dto.ClientDTO;
import com.thomaskavi.agendaki.entities.Client;
import com.thomaskavi.agendaki.entities.Role;
import com.thomaskavi.agendaki.repository.ClientRepository;
import com.thomaskavi.agendaki.repository.RoleRepository;
import com.thomaskavi.agendaki.services.exceptions.DatabaseException;
import com.thomaskavi.agendaki.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ClientService {

  @Autowired
  private RoleRepository roleRepository;

  @Autowired
  private ClientRepository repository;

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

    Role roleClient = roleRepository.findByAuthority("ROLE_CLIENT")
        .orElseThrow(() -> new ResourceNotFoundException("Perfil ROLE_CLIENT não encontrado"));

    entity.addRole(roleClient);

    entity.setProfessional(null);
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
  }

  protected Client authenticated() {
    try {
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      Jwt jwtPrincipal = (Jwt) authentication.getPrincipal();
      String username = jwtPrincipal.getClaim("username");
      return repository.findByEmail(username)
          .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
    } catch (Exception e) {
      throw new UsernameNotFoundException("Email não encontrado");
    }
  }

  @Transactional(readOnly = true)
  public ClientDTO getMe() {
    Client client = authenticated();
    return new ClientDTO(client);
  }
}
