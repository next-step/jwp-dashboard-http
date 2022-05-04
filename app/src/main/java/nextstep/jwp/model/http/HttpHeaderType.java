package nextstep.jwp.model.http;

import java.util.Arrays;
import nextstep.jwp.exception.HeaderNotFoundException;

public enum HttpHeaderType {

    CONNECTION("Connection"),
    CONTENT_TYPE("Content-Type"),
    CONTENT_LENGTH("Content-Length"),
    LOCATION("Location"),
    HOST("Host"),
    ACCEPT("Accept"),
    Date("Date");

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

    @Override
    public String toString() {
        return  headerType;
    }
}