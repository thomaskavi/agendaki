package com.thomaskavi.agendaki.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_service_offered")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceOffered {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private String description;
  private Double price;
  private Integer durationInMinutes;

  @ManyToOne
  @JoinColumn(name = "professional_id")
  private Professional professional;
}
