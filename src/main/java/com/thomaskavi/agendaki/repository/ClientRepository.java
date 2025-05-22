package com.thomaskavi.agendaki.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.thomaskavi.agendaki.entities.Client;
import com.thomaskavi.agendaki.projections.ClientDetailsProjection;

public interface ClientRepository extends JpaRepository<Client, Long> {

  @Query(nativeQuery = true, value = """
          SELECT c.email AS username, c.password, r.id AS roleId, r.authority
          FROM tb_client c
          INNER JOIN tb_client_role cr ON c.id = cr.client_id
          INNER JOIN tb_role r ON r.id = cr.role_id
          WHERE c.email = :email
      """)
  List<ClientDetailsProjection> searchClientAndRolesByEmail(String email);

  Optional<Client> findByEmail(String username);

}
