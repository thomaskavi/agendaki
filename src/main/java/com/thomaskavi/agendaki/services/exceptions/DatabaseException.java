package com.thomaskavi.agendaki.services.exceptions;

import org.springframework.dao.DataIntegrityViolationException;

public class DatabaseException extends RuntimeException {

  public DatabaseException(String msg, DataIntegrityViolationException e) {
    super(msg, e);
  }

  public DatabaseException(String msg) {
    super(msg);
  }
}