package com.demetriusdemiurge.bishop_starter.exceptions;

public class QueueOverflowException extends RuntimeException {
    public QueueOverflowException(String message) {
        super(message);
    }
}