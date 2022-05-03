package nextstep.jwp.model;

import java.util.ArrayList;
import java.util.List;
import nextstep.jwp.exception.HeaderNotFoundException;

public class HttpHeaders {

    private List<HttpHeader> headers ;

    public HttpHeaders() {
        this.headers = new ArrayList<>();
    }

    public void addHeader(String line){
        this.headers.add(HttpHeader.of(line));
    }

    public String getHeaderValueWithHeaderType(String headerType) {
        return getHttpHeaderWithHeaderType(headerType).getHeaderValue();
    }

    private HttpHeader getHttpHeaderWithHeaderType(String type) {
        return this.headers.stream()
            .filter(httpHeader -> httpHeader.isEqualHeaderType(type))
            .findFirst()
            .orElseThrow(() -> new HeaderNotFoundException("Header Type does not exist :" + type));
    }

}
