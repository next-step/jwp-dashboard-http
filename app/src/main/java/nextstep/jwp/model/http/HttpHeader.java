package nextstep.jwp.model.http;

public class HttpHeader {

    private HttpHeaderType headerType;
    private String headerValue;

    private HttpHeader(HttpHeaderType headerType, String headerValue) {
        this.headerType = headerType;
        this.headerValue = headerValue;
    }

    public static HttpHeader of(String headerLine) {
        String[] headers = headerLine.split(": ");
        return new HttpHeader(HttpHeaderType.of(headers[0]), headers[1]);
    }

    public Integer getContentLength() {
        if (isContentLength()) {
            return Integer.parseInt(headerValue);
        }
        return 0;
    }

    private boolean isContentLength() {
        return this.headerType.equals(HttpHeaderType.CONTENT_LENGTH);
    }

    public boolean isIfNoneMatch() {
        return this.headerType.equals(HttpHeaderType.IF_NONE_MATCH);
    }


    public String getHeaderValue() {
        return headerValue;
    }

    @Override
    public String toString() {
        return headerType + ": " + headerValue;
    }
}
