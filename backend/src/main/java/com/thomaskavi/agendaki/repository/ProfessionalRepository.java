package com.thomaskavi.agendaki.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.thomaskavi.agendaki.entities.Professional;
import com.thomaskavi.agendaki.projections.ProfessionalDetailsProjection;

@Repository
public interface ProfessionalRepository extends JpaRepository<Professional, Long> {

  @Query(nativeQuery = true, value = """
          SELECT p.email AS username, p.password, r.id AS roleId, r.authority
          FROM tb_professional p
          INNER JOIN tb_professional_role pr ON p.id = pr.professional_id
          INNER JOIN tb_role r ON r.id = pr.role_id
          WHERE p.email = :email
      """)
  List<ProfessionalDetailsProjection> searchProfessionalAndRolesByEmail(String email);

  Optional<Professional> findByEmail(String email);

  Page<Professional> findAll(Pageable pageable);

  @Query("SELECT p FROM Professional p LEFT JOIN FETCH p.services WHERE p.slug = :slug")
  Optional<Professional> findBySlug(@Param("slug") String slug);

}
