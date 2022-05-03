package nextstep.jwp.model;

import java.util.Arrays;
import nextstep.jwp.exception.HeaderNotFoundException;

public enum HttpHeaderType {

    CONNECTION("Connection"),
    CONTENT_TYPE("Content-Type"),
    CONTENT_LENGTH("Content-Length"),
    LOCATION("Location");

    private final String headerType;

    public static HttpHeaderType of(String type) {
        return Arrays.stream(values())
            .filter(httpHeaderType -> httpHeaderType.isEqualHeaderType(type))
            .findFirst()
            .orElseThrow(() -> new HeaderNotFoundException("Header Type does not exist : " + type));
    }

    HttpHeaderType(String headerType) {
        this.headerType = headerType;
    }

    public String getHeaderType() {
        return headerType;
    }

    public boolean isEqualHeaderType(String type) {
        return this.headerType.equals(type);
    }
}
