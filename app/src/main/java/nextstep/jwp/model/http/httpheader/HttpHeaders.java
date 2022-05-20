package nextstep.jwp.model.http.httpheader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class HttpHeaders {

    private List<HttpHeader> headers;

    public HttpHeaders() {
        this.headers = new ArrayList<>();
    }

    public HttpHeaders(String[] headers) {
        this.headers = Arrays.stream(headers)
            .map(HttpHeader::of)
            .collect(Collectors.toList());
    }

    public void addHeader(String line) {
        this.headers.add(HttpHeader.of(line));
    }

    public String headerToString(HttpHeaderType type, String value) {
        return type.getHeaderType() + ": " + value + " ";
    }

    public void addTypeHeader(HttpHeaderType type, String value) {
        addHeader(headerToString(type, value));
    }

    public Integer getContentLength() {
        return headers.stream()
            .mapToInt(httpHeader -> httpHeader.getContentLength())
            .max()
            .orElse(0);
    }

    public boolean hasIfNoneMatch() {
        return headers.stream()
            .anyMatch(httpHeader -> httpHeader.isIfNoneMatch());
    }

    public String getIfNoneMatch() {
        return headers.stream()
            .filter(httpHeader -> httpHeader.isIfNoneMatch())
            .findFirst()
            .orElseThrow(() -> new RuntimeException("ETag does not exist in request headers"))
            .getHeaderValue();
    }

    public boolean hasSessionIdCookie() {
        return headers.stream()
            .anyMatch(httpHeader -> httpHeader.hasSessionIdCookie());
    }

    @Override
    public String toString() {
        return headers.stream()
            .map(HttpHeader::toString)
            .collect(Collectors.joining("\r\n")) + "\r\n";
    }

}
