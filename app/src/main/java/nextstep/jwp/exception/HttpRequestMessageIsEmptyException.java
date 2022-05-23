package nextstep.jwp.exception;

public class HttpRequestMessageIsEmptyException extends RuntimeException {

    public HttpRequestMessageIsEmptyException(String message) {
        super(message);
    }

    public HttpRequestMessageIsEmptyException(String message, Throwable cause) {
        super(message, cause);
    }

    public HttpRequestMessageIsEmptyException(Throwable cause) {
        super(cause);
    }
}
