package com.ptac.exception;

/**
 * This class serves as a business logic exception of the system.
 * @author Mardolfh Del Rosario
 *
 */
public class ServiceException extends Exception {

    private static final long serialVersionUID = -602147848119964101L;

    /**
     * Common constructor.
     * @param message exception message.
     */
    public ServiceException(String message) {
        super(message);
    }

    /**
     * Constructor that includes the throwable reference.
     * @param message   exception message.
     * @param cause     Throwable reference.
     */
    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

}
