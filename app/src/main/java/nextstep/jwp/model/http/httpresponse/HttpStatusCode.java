package nextstep.jwp.model.http.httpresponse;

import java.util.Arrays;
import nextstep.jwp.exception.StatusCodeNotFoundException;

public enum HttpStatusCode {

    OK(200, "OK"),
    ACCEPTED(202, "Accepted"),
    NO_CONTENT(204, "No Content"),
    MOVED_PERMANENTLY(301, "Moved Permanently"),
    FOUND(302, "Found"),
    NOT_MODIFIED(304, "Not Modified"),
    BAD_REQUEST(400, "Bad Request"),
    UNAUTHORIZED(401, "Unauthorized"),
    FORBIDDEN(403, "Forbidden"),
    NOT_FOUND(404, "Not Found"),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error");

    private final int statusCode;
    private final String statusMessage;

    HttpStatusCode(int statusCode, String statusMessage) {
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
    }

    public static HttpStatusCode of(int statusCode) {
        return Arrays.stream(values())
            .filter(httpStatusCode -> httpStatusCode.getStatusCode() == statusCode)
            .findFirst()
            .orElseThrow(() -> new StatusCodeNotFoundException("Status Code not found with : " + statusCode));
    }

    public int getStatusCode() {
        return statusCode;
    }

    @Override
    public String toString() {
        return statusCode + " " + statusMessage;
    }
}
