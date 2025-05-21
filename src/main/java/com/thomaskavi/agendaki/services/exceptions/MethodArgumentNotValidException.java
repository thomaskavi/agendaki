package com.thomaskavi.agendaki.services.exceptions;

public class MethodArgumentNotValidException extends RuntimeException {

  MethodArgumentNotValidException(String msg) {
    super(msg);
  }
}