package nextstep.jwp.model.http;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import nextstep.jwp.model.http.httpresponse.ContentType;

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

    public void addContentTypeHeader(String type) {
        addHeader(HttpHeaderType.CONTENT_TYPE.getHeaderType() + ": " + ContentType.contentType(type)
            + " ");
    }

    public void addContentLengthHeader(int contentLength) {
        addHeader(HttpHeaderType.CONTENT_LENGTH.getHeaderType() + ": " + contentLength + " ");
    }

    public void addLocationHeader(String location) {
        addHeader(HttpHeaderType.LOCATION.getHeaderType() + ": " + location + " ");
    }

    public void addEtagHeader(String etag) {
        addHeader(HttpHeaderType.E_TAG.getHeaderType() + ": " + etag + " ");
    }

    public Integer getContentLength() {
        return headers.stream()
            .mapToInt(httpHeader -> httpHeader.getContentLength())
            .max()
            .orElse(0);
    }

    public boolean hasETag() {
        return headers.stream()
            .anyMatch(httpHeader -> httpHeader.isETag());
    }

    public String getETag() {
        return headers.stream()
            .filter(httpHeader -> httpHeader.isETag())
            .findFirst()
            .orElseThrow(() -> new RuntimeException("ETag does not exist in request headers"))
            .getHeaderValue();
    }


    @Override
    public String toString() {
        return headers.stream()
            .map(HttpHeader::toString)
            .collect(Collectors.joining("\r\n")) + "\r\n";
    }

}
