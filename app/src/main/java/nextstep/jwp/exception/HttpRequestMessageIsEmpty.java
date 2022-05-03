package nextstep.jwp.exception;

public class HttpRequestMessageIsEmpty extends RuntimeException{

    public HttpRequestMessageIsEmpty(String message) {
        super(message);
    }

    public HttpRequestMessageIsEmpty(String message, Throwable cause) {
        super(message, cause);
    }

    public HttpRequestMessageIsEmpty(Throwable cause) {
        super(cause);
    }
}
