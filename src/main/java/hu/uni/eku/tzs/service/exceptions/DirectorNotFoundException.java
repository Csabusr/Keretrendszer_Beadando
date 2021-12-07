package hu.uni.eku.tzs.service.exceptions;

public class DirectorNotFoundException extends Exception {

    public DirectorNotFoundException() {
    }

    public DirectorNotFoundException(String message) {
        super(message);
    }

    public DirectorNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public DirectorNotFoundException(Throwable cause) {
        super(cause);
    }

    public DirectorNotFoundException(String message, Throwable cause, boolean enableSuppression,
                                  boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
