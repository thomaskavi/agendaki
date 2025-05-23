package com.thomaskavi.agendaki.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.thomaskavi.agendaki.dto.ProfessionalDTO;
import com.thomaskavi.agendaki.dto.ProfessionalDetailsDTO;
import com.thomaskavi.agendaki.dto.ProfessionalPublicDTO;
import com.thomaskavi.agendaki.entities.Professional;
import com.thomaskavi.agendaki.entities.Role;
import com.thomaskavi.agendaki.repository.ProfessionalRepository;
import com.thomaskavi.agendaki.repository.RoleRepository;
import com.thomaskavi.agendaki.services.exceptions.DatabaseException;
import com.thomaskavi.agendaki.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ProfessionalService {

  @Autowired
  private AuthService authService;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private RoleRepository roleRepository;

  @Autowired
  private ProfessionalRepository repository;

  @Transactional(readOnly = true)
  public List<ProfessionalDetailsDTO> findAll() {
    List<Professional> result = repository.findAll();
    return result.stream().map(x -> new ProfessionalDetailsDTO(x)).toList();
  }

  @Transactional(readOnly = true)
  public ProfessionalDetailsDTO findById(Long id) {
    Professional professional = repository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Profissional não encontrado"));
    return new ProfessionalDetailsDTO(professional);
  }

  @Transactional
  public ProfessionalDTO insert(ProfessionalDTO dto) {
    try {
      if (repository.findByEmail(dto.getEmail()).isPresent()) {
        throw new DatabaseException("Email já está em uso");
      }

      Professional entity = new Professional();
      copyDtoToEntity(dto, entity);
      entity.setPassword(passwordEncoder.encode(dto.getPassword()));

      Role role = roleRepository.findByAuthority("ROLE_PROFESSIONAL")
          .orElseThrow(() -> new ResourceNotFoundException("Role 'ROLE_PROFESSIONAL' não encontrada"));
      entity.getRoles().add(role);

      entity = repository.save(entity);
      return new ProfessionalDTO(entity);
    } catch (DataIntegrityViolationException e) {
      if (e.getMessage() != null && e.getMessage().toLowerCase().contains("slug")) {
        throw new DatabaseException("Slug já está em uso", e);
      }
      throw new DatabaseException("Erro de integridade de dados", e);
    }
  }

  @Transactional
  public ProfessionalDTO update(Long id, ProfessionalDTO dto) {
    // Só deixa atualizar se for o próprio profissional ou admin
    authService.validateSelfOrAdmin(id);

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
    // Só deixa deletar se for admin (exemplo)
    authService.validateAdmin();

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
    entity.setBirthDate(dto.getBirthDate());
    entity.setProfileImageUrl(dto.getProfileImageUrl());
  }

  protected Professional authenticated() {
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
  public ProfessionalDTO getMe() {
    Professional professional = authenticated();
    return new ProfessionalDTO(professional);
  }

  @Transactional(readOnly = true)
  public ProfessionalPublicDTO findBySlug(String slug) {
    Professional professional = repository.findBySlug(slug)
        .orElseThrow(() -> new ResourceNotFoundException("Profissional não encontrado"));
    return new ProfessionalPublicDTO(professional);
  }

}
