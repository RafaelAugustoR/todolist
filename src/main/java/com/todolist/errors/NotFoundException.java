package com.todolist.errors;

public class NotFoundException extends RuntimeException {

    private ErrorCodes errorCode;

    public NotFoundException(String message, ErrorCodes errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public ErrorCodes getErrorCode() {
        return errorCode;
    }
}
