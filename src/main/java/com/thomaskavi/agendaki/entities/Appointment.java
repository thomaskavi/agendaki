package com.thomaskavi.agendaki.entities;

import java.time.LocalDateTime;

import com.thomaskavi.agendaki.enums.AppointmentStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "tb_appointment")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Appointment {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private LocalDateTime startTime;

  @Column(nullable = false)
  private LocalDateTime endTime;

  @ManyToOne
  @JoinColumn(name = "client_id")
  private User client;

  @ManyToOne
  @JoinColumn(name = "service_id")
  private ServiceOffered service;

  @ManyToOne
  @JoinColumn(name = "professional_id")
  private Professional professional;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private AppointmentStatus status = AppointmentStatus.PENDING;

}
