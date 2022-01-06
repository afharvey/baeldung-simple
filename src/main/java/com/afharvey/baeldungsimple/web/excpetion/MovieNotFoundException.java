package com.afharvey.baeldungsimple.web.excpetion;

public class MovieNotFoundException extends RuntimeException {

    public MovieNotFoundException() {
        super();
    }

    public MovieNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public MovieNotFoundException(final String message) {
        super(message);
    }

    public MovieNotFoundException(final Throwable cause) {
        super(cause);
    }
}
