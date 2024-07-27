package com.practice.my_vocabulary.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }

    public static NotFoundException vocabularyNotFound() {
        return new NotFoundException("Could not find vocabulary.");
    }
}
