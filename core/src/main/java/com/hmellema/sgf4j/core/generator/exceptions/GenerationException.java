package com.hmellema.sgf4j.core.generator.exceptions;

/**
 * Indicates that code generation failed.
 */
public class GenerationException extends RuntimeException {
  public GenerationException(String msg) {
    super(msg);
  }

  public GenerationException(String msg, Throwable cause) {
    super(msg, cause);
  }
}
