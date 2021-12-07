package hu.uni.eku.tzs.service.exceptions;

public class MovieGenreNotFoundException extends Exception {

    public MovieGenreNotFoundException() {
    }

    public MovieGenreNotFoundException(String message) {
        super(message);
    }

    public MovieGenreNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public MovieGenreNotFoundException(Throwable cause) {
        super(cause);
    }

    public MovieGenreNotFoundException(String message, Throwable cause, boolean enableSuppression,
                                     boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
