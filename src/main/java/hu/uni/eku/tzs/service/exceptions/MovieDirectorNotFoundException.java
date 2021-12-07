package hu.uni.eku.tzs.service.exceptions;

public class MovieDirectorNotFoundException extends Exception {

    public MovieDirectorNotFoundException() {
    }

    public MovieDirectorNotFoundException(String message) {
        super(message);
    }

    public MovieDirectorNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public MovieDirectorNotFoundException(Throwable cause) {
        super(cause);
    }

    public MovieDirectorNotFoundException(String message, Throwable cause, boolean enableSuppression,
                                       boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
