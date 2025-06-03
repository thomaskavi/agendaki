package com.thomaskavi.agendaki.services;

import static org.mockito.ArgumentMatchers.any;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.thomaskavi.agendaki.entities.Professional;
import com.thomaskavi.agendaki.repository.ProfessionalRepository;
import com.thomaskavi.agendaki.services.exceptions.ResourceNotFoundException;
import com.thomaskavi.agendaki.tests.ProfessionalFactory;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(SpringExtension.class)
public class ProfessionalServiceTests {

  @InjectMocks
  private ProfessionalService service;

  @Mock
  private ProfessionalRepository repository;

  private Professional professional;
  private List<Professional> professionalList;

  private Long existingId, nonExistingId, dependentId;

  @BeforeEach
  void setUp() throws Exception {
    professional = ProfessionalFactory.createProfessional();
    professionalList = new ArrayList<>();
    professionalList.add(professional);
    existingId = 1L;
    dependentId = 3L;
    nonExistingId = 100L;

    Mockito.when(repository.findAll()).thenReturn(professionalList);
    Mockito.when(repository.findById(existingId)).thenReturn(Optional.of(professional));
    Mockito.when(repository.findById(nonExistingId)).thenReturn(Optional.empty());
    Mockito.when(repository.save(any())).thenReturn(professional);
    Mockito.when(repository.getReferenceById(existingId)).thenReturn(professional);
    Mockito.when(repository.getReferenceById(nonExistingId)).thenThrow(EntityNotFoundException.class);
    Mockito.when(repository.existsById(existingId)).thenReturn(true);
    Mockito.when(repository.existsById(dependentId)).thenReturn(true);
    Mockito.when(repository.existsById(nonExistingId)).thenReturn(false);
    Mockito.doNothing().when(repository).deleteById(existingId);
    Mockito.doThrow(DataIntegrityViolationException.class).when(repository).deleteById(dependentId);
  }

  // @Test
  // public void
  // findAllShouldReturnListOfProfessionalDetailsDTOWhenUserAuthenticated() {
  // List<ProfessionalDetailsDTO> result = service.findAll();

  // Assertions.assertEquals(1, result.size());
  // Assertions.assertEquals(professional.getId(), result.get(0).getId());
  // Assertions.assertEquals(professional.getName(), result.get(0).getName());
  // Assertions.assertEquals(professional.getEmail(), result.get(0).getEmail());
  // Assertions.assertEquals(professional.getPhone(), result.get(0).getPhone());
  // Assertions.assertEquals(professional.getSlug(), result.get(0).getSlug());
  // Assertions.assertEquals(professional.getProfession(),
  // result.get(0).getProfession());
  // }

  @Test
  public void findByIdShouldReturnDataWhenIdExists() {
    Optional<Professional> result = repository.findById(existingId);

    Assertions.assertNotNull(result);
    Assertions.assertEquals(professional.getId(), result.get().getId());
    Assertions.assertEquals(professional.getName(), result.get().getName());
    Assertions.assertEquals(professional.getEmail(), result.get().getEmail());
  }

  @Test
  public void findByIdShouldThrowResourceNotFoundExceptionWhenNonExistingId() {
    Assertions.assertThrows(ResourceNotFoundException.class, () -> {
      service.findById(nonExistingId);
    });
  }

}
