package com.thomaskavi.agendaki.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "tb_available_slot")
public class AvailableSlot {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  private Professional professional;
  private LocalDateTime startTime;
  private LocalDateTime endTime;
  private boolean isBooked;

  public AvailableSlot() {
  }

  public AvailableSlot(Long id, Professional professional, LocalDateTime startTime, LocalDateTime endTime,
      boolean isBooked) {
    this.id = id;
    this.professional = professional;
    this.startTime = startTime;
    this.endTime = endTime;
    this.isBooked = isBooked;
  }

}
