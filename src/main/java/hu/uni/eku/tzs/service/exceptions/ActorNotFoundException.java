package hu.uni.eku.tzs.service.exceptions;

public class ActorNotFoundException extends Exception {

    public ActorNotFoundException() {
    }

    public ActorNotFoundException(String message) {
        super(message);
    }

    public ActorNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ActorNotFoundException(Throwable cause) {
        super(cause);
    }

    public ActorNotFoundException(String message, Throwable cause, boolean enableSuppression,
                                 boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
