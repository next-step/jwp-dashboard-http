package nextstep.jwp.model.http.httpheader;

import java.util.Arrays;
import nextstep.jwp.exception.HeaderNotFoundException;

public enum HttpHeaderType {

    CONNECTION("Connection"),
    CONTENT_TYPE("Content-Type"),
    CONTENT_LENGTH("Content-Length"),
    CONTENT_ENCODING("Content-Encoding"),
    LOCATION("Location"),
    HOST("Host"),
    ACCEPT("Accept"),
    DATE("Date"),
    CACHE_CONTROL("Cache-Control"),
    SEC_CH_UA("sec-ch-ua"),
    SEC_CH_UA_MOBILE("sec-ch-ua-mobile"),
    SEC_CH_UA_PLATFORM("sec-ch-ua-platform"),
    UPGRADE_INSECURE_REQUESTS("Upgrade-Insecure-Requests"),
    USER_AGENT("User-Agent"),
    SEC_FETCH_SITE("Sec-Fetch-Site"),
    SEC_FETCH_MODE("Sec-Fetch-Mode"),
    SEC_FETCH_USER("Sec-Fetch-User"),
    SEC_FETCH_DEST("Sec-Fetch-Dest"),
    ACCEPT_ENCODING("Accept-Encoding"),
    ACCEPT_LANGUAGE("Accept-Language"),
    COOKIE("Cookie"),
    SET_COOKIE("Set-Cookie"),
    PRAGMA("Pragma"),
    REFERER("Referer"),
    ORIGIN("Origin"),
    E_TAG("ETag"),
    IF_NONE_MATCH("If-None-Match");


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
        return headerType;
    }
}
