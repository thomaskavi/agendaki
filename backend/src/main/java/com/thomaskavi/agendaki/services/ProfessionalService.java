package com.thomaskavi.agendaki.services;

import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.thomaskavi.agendaki.dto.ProfessionalDTO;
import com.thomaskavi.agendaki.dto.ProfessionalDetailsDTO;
import com.thomaskavi.agendaki.dto.ProfessionalInsertResponseDTO;
import com.thomaskavi.agendaki.dto.ProfessionalPublicDTO;
import com.thomaskavi.agendaki.dto.ProfessionalSignupDTO;
import com.thomaskavi.agendaki.dto.ProfessionalUpdateDTO;
import com.thomaskavi.agendaki.dto.UriDTO;
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
  private S3Service s3Service;

  @Autowired
  private RoleRepository roleRepository;

  @Autowired
  private ProfessionalRepository repository;

  @Transactional(readOnly = true)
  public Page<ProfessionalDetailsDTO> findAll(Pageable pageable) {
    Page<Professional> page = repository.findAll(pageable);
    return page.map(ProfessionalDetailsDTO::new);
  }

  @Transactional(readOnly = true)
  public ProfessionalDetailsDTO findById(Long id) {
    Professional professional = repository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Profissional não encontrado"));
    return new ProfessionalDetailsDTO(professional);
  }

  @Transactional
  public ProfessionalInsertResponseDTO createProfessional(ProfessionalSignupDTO dto) {
    try {
      if (repository.findByEmail(dto.getEmail()).isPresent()) {
        throw new DatabaseException("Email já está em uso");
      }

      Professional entity = new Professional();
      copySignupDtoToEntity(dto, entity);
      entity.setPassword(passwordEncoder.encode(dto.getPassword()));

      Role role = roleRepository.findByAuthority("ROLE_PROFESSIONAL")
          .orElseThrow(() -> new ResourceNotFoundException("Role 'ROLE_PROFESSIONAL' não encontrada"));
      entity.getRoles().add(role);

      entity = repository.save(entity);
      return new ProfessionalInsertResponseDTO(entity);
    } catch (DataIntegrityViolationException e) {
      if (e.getMessage() != null && e.getMessage().toLowerCase().contains("slug")) {
        throw new DatabaseException("Slug já está em uso", e);
      }
      throw new DatabaseException("Erro de integridade de dados", e);
    }
  }

  @Transactional
  public ProfessionalDTO adminInsert(ProfessionalSignupDTO dto) {
    try {
      if (repository.findByEmail(dto.getEmail()).isPresent()) {
        throw new DatabaseException("Email já está em uso");
      }

      Professional entity = new Professional();
      copySignupDtoToEntity(dto, entity);
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

  // Método auxiliar para mapear ProfessionalSignupDTO para Professional entity
  private void copySignupDtoToEntity(ProfessionalSignupDTO dto, Professional entity) {
    // Campos de UserSignupDTO (base)
    entity.setName(dto.getName());
    entity.setEmail(dto.getEmail());
    entity.setPhone(dto.getPhone());
    entity.setBirthDate(dto.getBirthDate());
    // A senha será definida separadamente e criptografada

    // Campos específicos de ProfessionalSignupDTO
    entity.setSlug(dto.getSlug());
    entity.setProfession(dto.getProfession());

    // Lembre-se: O campo 'services' não vem no DTO de signup, ele é preenchido
    // depois
  }

  @Transactional
  public ProfessionalUpdateDTO update(Long id, ProfessionalUpdateDTO dto) {
    authService.validateSelfOrAdmin(id);

    try {
      Professional entity = repository.getReferenceById(id);
      copyDtoToEntity(dto, entity);
      entity = repository.save(entity);
      return new ProfessionalUpdateDTO(entity);
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

  private void copyDtoToEntity(ProfessionalUpdateDTO dto, Professional entity) {
    if (dto.getName() != null) {
      entity.setName(dto.getName());
    }
    if (dto.getPhone() != null) {
      entity.setPhone(dto.getPhone());
    }
    if (dto.getBirthDate() != null) {
      entity.setBirthDate(dto.getBirthDate());
    }
    if (dto.getSlug() != null) {
      entity.setSlug(dto.getSlug());
    }
    if (dto.getProfession() != null) {
      entity.setProfession(dto.getProfession());
    }
    if (dto.getProfileImageUrl() != null) {
      entity.setProfileImageUrl(dto.getProfileImageUrl());
    }
  }

  @Transactional
  public UriDTO uploadProfileImage(MultipartFile file) {
    Professional professional = authenticated(); // Busca o profissional logado

    URL url = s3Service.uploadFile(file, professional.getName()); // Usa o nome do profissional no nome do arquivo

    professional.setProfileImageUrl(url.toString()); // Atualiza a URL no banco
    repository.save(professional);

    return new UriDTO(url.toString()); // Retorna para o controller
  }

}
