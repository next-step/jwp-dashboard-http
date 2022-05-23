package nextstep.jwp.exception;

public class SessionAttributeNotFoundException extends RuntimeException {

    public SessionAttributeNotFoundException(String message) {
        super(message);
    }

    public SessionAttributeNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public SessionAttributeNotFoundException(Throwable cause) {
        super(cause);
    }
}
