package nextstep.jwp.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import nextstep.jwp.exception.HeaderNotFoundException;

public class HttpHeaders {

    private List<HttpHeader> headers;

    public HttpHeaders() {
        this.headers = new ArrayList<>();
    }

    public void addHeader(String line) {
        this.headers.add(HttpHeader.of(line));
    }

//    public String getHeaderValueWithHeaderType(String headerType) {
//        return getHttpHeaderWithHeaderType(headerType).getHeaderValue();
//    }
//
//    private HttpHeader getHttpHeaderWithHeaderType(String type) {
//        return this.headers.stream()
//            .filter(httpHeader -> httpHeader.isEqualHeaderType(type))
//            .findFirst()
//            .orElseThrow(() -> new HeaderNotFoundException("Header Type does not exist :" + type));
//    }

    public void addContentTypeHeader(String type) {
        addHeader(HttpHeaderType.CONTENT_TYPE.getHeaderType() + ": " + ContentType.contentType(type)+ " ");
    }

    public void addContentLengthHeader(int contentLength) {
        addHeader(HttpHeaderType.CONTENT_LENGTH.getHeaderType() + ": " + contentLength + " ");
    }

    public void addLocationHeader(String location) {
        addHeader(HttpHeaderType.LOCATION.getHeaderType() + ": " + location + " ");
    }

    @Override
    public String toString() {
        return headers.stream()
            .map(httpHeader -> httpHeader.toString())
            .collect(Collectors.joining("\r\n")) + "\r\n";
    }
}
