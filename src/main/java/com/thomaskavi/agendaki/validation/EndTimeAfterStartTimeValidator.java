package com.thomaskavi.agendaki.validation;

import com.thomaskavi.agendaki.dto.CreateAvailableSlotDTO;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EndTimeAfterStartTimeValidator
    implements ConstraintValidator<EndTimeAfterStartTime, CreateAvailableSlotDTO> {

  @Override
  public boolean isValid(CreateAvailableSlotDTO dto, ConstraintValidatorContext context) {
    if (dto.getStartTime() == null || dto.getEndTime() == null) {
      return true;
    }
    return dto.getEndTime().isAfter(dto.getStartTime());
  }
}
