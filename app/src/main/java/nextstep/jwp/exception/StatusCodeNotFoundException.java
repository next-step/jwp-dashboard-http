package nextstep.jwp.exception;

public class StatusCodeNotFoundException extends RuntimeException {

    public StatusCodeNotFoundException(String message) {
        super(message);
    }

    public StatusCodeNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public StatusCodeNotFoundException(Throwable cause) {
        super(cause);
    }
}
