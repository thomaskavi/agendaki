package com.thomaskavi.agendaki.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.thomaskavi.agendaki.dto.PublicUserDTO;
import com.thomaskavi.agendaki.dto.UserMeDTO;
import com.thomaskavi.agendaki.dto.UserSignupDTO;
import com.thomaskavi.agendaki.dto.UserUpdateDTO;
import com.thomaskavi.agendaki.entities.Role;
import com.thomaskavi.agendaki.entities.User;
import com.thomaskavi.agendaki.projections.UserDetailsProjection;
import com.thomaskavi.agendaki.repository.RoleRepository;
import com.thomaskavi.agendaki.repository.UserRepository;
import com.thomaskavi.agendaki.services.exceptions.DatabaseException;
import com.thomaskavi.agendaki.services.exceptions.ResourceNotFoundException;
import com.thomaskavi.agendaki.util.CustomUserUtil;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UserService implements UserDetailsService {

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private RoleRepository roleRepository;

  @Autowired
  private UserRepository repository;

  @Autowired
  private CustomUserUtil customUserUtil;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    List<UserDetailsProjection> result = repository.searchUserAndRolesByEmail(username);
    if (result.size() == 0) {
      throw new UsernameNotFoundException("Email not found");
    }

    User user = new User();
    user.setEmail(result.get(0).getUsername());
    user.setPassword(result.get(0).getPassword());
    for (UserDetailsProjection projection : result) {
      user.addRole(new Role(projection.getRoleId(), projection.getAuthority()));
    }
    return user;
  }

  protected User authenticated() {
    try {
      String username = customUserUtil.getLoggedUsername();
      return repository.findByEmail(username).get();
    } catch (Exception e) {
      throw new UsernameNotFoundException("Email not found");
    }
  }

  @Transactional(readOnly = true)
  public UserMeDTO getMe() {
    User entity = authenticated();
    return new UserMeDTO(entity);
  }

  @Transactional(propagation = Propagation.SUPPORTS)
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public void delete(Long id) {

    if (!repository.existsById(id)) {
      throw new ResourceNotFoundException("Usuário não encontrado");
    }
    try {
      repository.deleteById(id);
    } catch (DataIntegrityViolationException e) {
      throw new DatabaseException("Não é possível remover um usuário com agendamento(s) em aberto", e);
    }
  }

  public Optional<User> findByEmail(String email) {
    return repository.findByEmail(email);
  }

  @Transactional
  public PublicUserDTO createUser(UserSignupDTO dto) {
    if (repository.findByEmail(dto.getEmail()).isPresent()) {
      throw new DatabaseException("Email já está em uso");
    }

    User user = new User();
    user.setName(dto.getName());
    user.setEmail(dto.getEmail());
    user.setPassword(passwordEncoder.encode(dto.getPassword()));
    user.setPhone(dto.getPhone());
    user.setBirthDate(dto.getBirthDate());

    Role role = roleRepository.findByAuthority("ROLE_CLIENT")
        .orElseThrow(() -> new ResourceNotFoundException("Role 'ROLE_CLIENT' não encontrada"));
    user.getRoles().add(role);

    user = repository.save(user);
    return new PublicUserDTO(user);
  }

  @Transactional
  public UserUpdateDTO updateUser(UserUpdateDTO dto) {
    User user = authenticated();
    try {
      copyDtoToEntity(dto, user);
      user = repository.getReferenceById(user.getId());
      user = repository.save(user);
      return new UserUpdateDTO(user);
    } catch (EntityNotFoundException e) {
      throw new ResourceNotFoundException("Usuário não encontrado");
    }
  }

  private void copyDtoToEntity(UserUpdateDTO dto, User entity) {
    if (dto.getName() != null) {
      entity.setName(dto.getName());
    }
    if (dto.getPhone() != null) {
      entity.setPhone(dto.getPhone());
    }
    if (dto.getBirthDate() != null) {
      entity.setBirthDate(dto.getBirthDate());
    }
  }
}