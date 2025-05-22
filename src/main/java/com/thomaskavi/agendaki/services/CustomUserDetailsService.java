package com.thomaskavi.agendaki.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.thomaskavi.agendaki.entities.Client;
import com.thomaskavi.agendaki.entities.Professional;
import com.thomaskavi.agendaki.entities.Role;
import com.thomaskavi.agendaki.projections.ProfessionalDetailsProjection;
import com.thomaskavi.agendaki.repository.ClientRepository;
import com.thomaskavi.agendaki.repository.ProfessionalRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

  @Autowired
  private ProfessionalRepository professionalRepository;

  @Autowired
  private ClientRepository clientRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    // Tenta buscar professional com roles detalhadas
    List<ProfessionalDetailsProjection> result = professionalRepository.searchProfessionalAndRolesByEmail(username);
    if (!result.isEmpty()) {
      Professional professional = new Professional();
      professional.setEmail(result.get(0).getUsername());
      professional.setPassword(result.get(0).getPassword());
      for (ProfessionalDetailsProjection projection : result) {
        professional.addRole(new Role(projection.getRoleId(), projection.getAuthority()));
      }
      return professional;
    }

    // Se não achou professional, tenta client
    Optional<Client> client = clientRepository.findByEmail(username);
    if (client.isPresent()) {
      return client.get();
    }

    throw new UsernameNotFoundException("User not found with email: " + username);
  }
}
