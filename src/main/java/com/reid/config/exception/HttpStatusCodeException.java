package com.reid.config.exception;

import java.util.ArrayList;
import java.util.List;

public class HttpStatusCodeException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  private List<String> messageKeys = new ArrayList<String>();

  public HttpStatusCodeException() {
    super();
  }

  public HttpStatusCodeException(final String messageKey) {
    super(messageKey);
    messageKeys.add(messageKey);
  }

  public HttpStatusCodeException(final String messageKey, final Throwable cause) {
    super(messageKey, cause);
    messageKeys.add(messageKey);
  }

}


