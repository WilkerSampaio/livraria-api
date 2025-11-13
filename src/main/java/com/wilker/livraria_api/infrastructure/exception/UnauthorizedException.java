package com.wilker.livraria_api.infrastructure.exception;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String message) {
        super(message);
    }
  public UnauthorizedException(String message, Throwable e) {
    super(message);
  }
}
