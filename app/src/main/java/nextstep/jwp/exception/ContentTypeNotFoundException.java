package nextstep.jwp.exception;

public class ContentTypeNotFoundException extends RuntimeException {

    public ContentTypeNotFoundException(String message) {
        super(message);
    }

    public ContentTypeNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ContentTypeNotFoundException(Throwable cause) {
        super(cause);
    }
}
