package hu.uni.eku.tzs.service.exceptions;

public class DirectorGenereNotFoundException extends Exception {

    public DirectorGenereNotFoundException() {
    }

    public DirectorGenereNotFoundException(String message) {
        super(message);
    }

    public DirectorGenereNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public DirectorGenereNotFoundException(Throwable cause) {
        super(cause);
    }

    public DirectorGenereNotFoundException(String message, Throwable cause, boolean enableSuppression,
                                  boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
