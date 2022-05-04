package nextstep.jwp.exception;

public class ParamNotFoundException extends RuntimeException {

    public ParamNotFoundException(String message) {
        super(message);
    }

    public ParamNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParamNotFoundException(Throwable cause) {
        super(cause);
    }
}
