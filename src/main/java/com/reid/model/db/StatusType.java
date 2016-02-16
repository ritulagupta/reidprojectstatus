package com.reid.model.db;

import com.reid.config.exception.HttpStatusCodeException;

public enum StatusType {
  
	ASSIGNED("ASSIGNED", 0),
	STARTED("STARTED", 1),
	COMPLETED("COMPLETED", 1);


  /**
   * Return the status type represented by the specified number.
   * @param number the number to be resolved
   * @return the associated instance
   * @throws HttpStatusCodeException if the number does not correspond to an instance
   */
  public static StatusType fromInt(int number) throws HttpStatusCodeException {
    for (StatusType batPortType : StatusType.values()) {
      if (batPortType.value == number) {
        return batPortType;
      }
    }
    throw new HttpStatusCodeException("INVALID_VALUE");
  }

  /**
   * Return the status type  represented by the specified string.
   * @param s the string to be resolved
   * @return the associated instance
   * @throws HttpStatusCodeException if the string does not correspond to an instance
   */
  public static StatusType fromString(String s) throws HttpStatusCodeException {
    for (StatusType statusType : StatusType.values()) {
      if (statusType.toString().equals(s)) {
        return statusType;
      }
    }
    throw new HttpStatusCodeException("INVALID_VALUE");
  }

  /**
   * Return the status type represented by the specified string.
   * @param fieldName provides more information for error reporting
   * @param s the string to be resolved
   * @return the associated instance
   * @throws SchedulerException if the string does not correspond to an instance
   */
  public static StatusType fromString(String fieldName, String s) throws HttpStatusCodeException {
    for (StatusType statusType: StatusType.values()) {
      if (statusType.toString().equals(s)) {
        return statusType;
      }
    }
    throw new HttpStatusCodeException("INVALID_VALUE");
  }

  /**
   * String representation of this instance.
   */
  private String text;

  /**
   * Integer value of this instance
   */
  private int value;

  /**
   * Private constructor to prevent casual instantiation.
   * @param aText the string representation of the item
   * @param aValue the value associated with the item
   */
  private StatusType(String aText, int aValue) {
    text = aText;
    value = aValue;
  }

  /**
   * Get the textual value of the instance
   * @return text value
   */
  public String getText() {
    return text;
  }

  /**
   * Get the numeric value of the instance
   * @return integer value
   */
  public int getValue() {
    return value;
  }

  /**
   * Return the textual representation of the instance.
   * @return the string representation
   */
  public String toString() {
    return text;
  }

}
