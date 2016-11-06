package de.stuttgart.hdm.csm.pk070.weather.exception;

/**
 * @author patrick.kleindienst
 */
public class BadApiResponseException extends RuntimeException {

    public BadApiResponseException(String message) {
        super(message);
    }
}
