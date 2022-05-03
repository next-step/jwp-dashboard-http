package nextstep.jwp.model;

public class HttpHeader {

    private HttpHeaderType headerType;
    private String headerValue;

    public HttpHeader(HttpHeaderType headerType, String headerValue) {
        this.headerType = headerType;
        this.headerValue = headerValue;
    }

    public HttpHeaderType getHeaderType() {
        return headerType;
    }

    public String getHeaderValue() {
        return headerValue;
    }

    public boolean isEqualHeaderType(String type) {
        return headerType.isEqualHeaderType(type);
    }

}
