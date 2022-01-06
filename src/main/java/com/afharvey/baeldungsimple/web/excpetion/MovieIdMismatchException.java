package com.afharvey.baeldungsimple.web.excpetion;

public class MovieIdMismatchException extends RuntimeException {

    public MovieIdMismatchException() {
        super();
    }

    public MovieIdMismatchException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public MovieIdMismatchException(final String message) {
        super(message);
    }

    public MovieIdMismatchException(final Throwable cause) {
        super(cause);
    }
}