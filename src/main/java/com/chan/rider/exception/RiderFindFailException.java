package com.chan.rider.exception;

public class RiderFindFailException extends RuntimeException{
    public RiderFindFailException() {
    }

    public RiderFindFailException(String message) {
        super(message);
    }

    public RiderFindFailException(String message, Throwable cause) {
        super(message, cause);
    }
}
