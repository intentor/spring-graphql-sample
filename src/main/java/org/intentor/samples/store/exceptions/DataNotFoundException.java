package org.intentor.samples.store.exceptions;

/**
 * Exception thrown when no data is found.
 */
public class DataNotFoundException extends RuntimeException {
    public DataNotFoundException(String entity, String id) {
        super(String.format("could not find %s with ID %s", entity, id));
    }
}