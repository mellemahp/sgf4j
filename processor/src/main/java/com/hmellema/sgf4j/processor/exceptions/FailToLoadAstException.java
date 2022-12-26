package com.hmellema.sgf4j.processor.exceptions;

public class FailToLoadAstException extends RuntimeException {
  public FailToLoadAstException(String msg) {
    super(msg);
  }

  public FailToLoadAstException(String msg, Throwable cause) {
    super(msg, cause);
  }
}
