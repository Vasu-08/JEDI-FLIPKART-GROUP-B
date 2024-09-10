package com.flipkart.exceptions;

/**
 * Custom exception to handle invalid data inputs.
 */
public class InvalidDataException extends Exception {

    /**
     * Constructor to create an InvalidDataException with a message.
     *
     * @param message Error message
     */
    public InvalidDataException(String message) {
        super(message);
    }
}
