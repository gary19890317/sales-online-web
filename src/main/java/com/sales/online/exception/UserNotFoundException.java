package com.sales.online.exception;

public class UserNotFoundException extends RuntimeException {
  /** */
  private static final long serialVersionUID = 1L;

  public UserNotFoundException() {
    super("Usuario No encontrado");
  }
}
