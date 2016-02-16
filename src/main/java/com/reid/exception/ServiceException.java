package com.reid.exception;

import java.io.Serializable;

public class ServiceException extends RuntimeException implements Serializable {

	private static final long serialVersionUID = 7246823414090113140L;	
	
	private String messageCode;
	private String detailMessage;

	  
	  // Constructors ---------------------------------------------------------------------------------------------------

  /**
   * Creates a ServiceException
   * @param aMessageCode MessageCode
   */
	public ServiceException(String aMessageCode) {
	  messageCode = aMessageCode;
	}

  /**
   * Creates a ServiceException from the specified attributes
   * @param aMessageCode the message code identifying the type of error
   * @param aSourceException the exception that caused the reported error
   */
  public ServiceException(String aMessageCode, Exception aSourceException) {
    this(aMessageCode);
    initCause(aSourceException);
  }

  /**
   * Creates a ServiceException from the specified attributes
   * @param aMessageCode the message code identifying the type of error
   * @param aDetailMessage the text describing the error in detail
   */
  public ServiceException(String aMessageCode, String aDetailMessage) {
    this(aMessageCode);
    detailMessage = aDetailMessage;
  }

  /**
   * Creates a ServiceException from the specified attributes
   * @param aMessageCode the message code identifying the type of error
   * @param aDetailMessage the text describing the error in detail
   * @param aSourceException the exception that caused the reported error
   */
  public ServiceException(String aMessageCode, String aDetailMessage, Exception aSourceException) {
    this(aMessageCode, aSourceException);
    detailMessage = aDetailMessage;
  }
	  
	  
  // Accessors ---------------------------------------------------------------------------------------------------

  /**
   * Returns error code associated with this Exception
   * @return MessageCode
   */
  public String getMessageCode() {
    return messageCode;
  }

  /**
   * Returns human readable error message for GUI/Logging purposes
   * @return String
   */
  public String getDetailMessage() {
    return detailMessage;
  }

  /**
   * Returns human readable error message for GUI/Logging purposes
   * @return String
   */
  @Override
  public String getMessage() {
    String s = "";
    if (messageCode != null) {
      s += messageCode.toString();
    }
    if (super.getMessage() != null) {
      s += "(" + super.getMessage() + ")";
    }
    return s;
  }

  
  /**
   * Gets the exception message of the root cause of this reporting exception.
   * This method never returns null but may return an empty string if there is no root cause.
   * @return a concatenation of the detail message (if any) and the original exception's getMessage() (if any)
   */
  public String getRootCauseString() {
    String message = "";
    if (detailMessage != null && detailMessage.length() > 0) {
      message = detailMessage;
      if (getCause() != null) {
        message += " (" + getCause().getMessage() + ")";
      }
    }
    else if (getCause() != null) {
      message = getCause().getMessage();
    }
    return message;
  }
}
