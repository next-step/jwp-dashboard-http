package nextstep.jwp.exception;

public class SessionAttributeInvalidException extends RuntimeException {

    public SessionAttributeInvalidException(String message) {
        super(message);
    }

    public SessionAttributeInvalidException(String message, Throwable cause) {
        super(message, cause);
    }

    public SessionAttributeInvalidException(Throwable cause) {
        super(cause);
    }
}
