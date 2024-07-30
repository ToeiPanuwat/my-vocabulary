package com.practice.my_vocabulary.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(long id) {
        super("Could not find vocabulary ID : " + id);
    }

    public static NotFoundException vocabularyNotFound() {
        return new NotFoundException("Could not find vocabulary.");
    }
}
