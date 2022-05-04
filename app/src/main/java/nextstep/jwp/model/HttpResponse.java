package nextstep.jwp.model;

import java.io.OutputStream;
import java.util.Arrays;

public class HttpResponse {

    private HttpStatusLine httpStatusLine;
    private HttpHeaders httpHeaders;
    private byte[] body;

    public HttpResponse(String type, String httpVersion, int statusCode, byte[] body) {
        this.httpStatusLine = new HttpStatusLine(httpVersion, statusCode);
        this.body = body;
        addHeaders(type, statusCode);
    }

    public void addHeaders(String type, int statusCode) {
        this.httpHeaders = new HttpHeaders();
        if (statusCode == 200) {
            addOkHeader(type);
        }
        if (statusCode == 304) {
            addFoundHeader(type, "/index.html");
        }
    }

    private void addOkHeader(String type) {
        httpHeaders.addContentTypeHeader(type);
        httpHeaders.addContentLengthHeader(this.body.length);
    }

    private void addFoundHeader(String type, String location) {
        httpHeaders.addContentTypeHeader(type);
        httpHeaders.addContentLengthHeader(this.body.length);
        httpHeaders.addLocationHeader(location);
    }

    @Override
    public String toString() {
        return httpStatusLine + "\r\n" + httpHeaders + "\r\n" + new String(body);
    }

    public byte[] getBytes() {
        return this.toString().getBytes();
    }
}
