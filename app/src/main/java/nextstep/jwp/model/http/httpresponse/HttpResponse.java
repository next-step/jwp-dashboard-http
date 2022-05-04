package nextstep.jwp.model.http.httpresponse;

import nextstep.jwp.model.http.HttpHeaders;

public class HttpResponse {

    private HttpStatusLine httpStatusLine;
    private HttpHeaders httpHeaders = new HttpHeaders();
    private byte[] body;

    public HttpResponse(HttpStatusLine httpStatusLine, byte[] body) {
        this.httpStatusLine = httpStatusLine;
        this.body = body;
    }

    public static HttpResponse ok(String type, String httpVersion, byte[] body) {
        HttpResponse httpResponse = new HttpResponse(new HttpStatusLine(httpVersion, 200), body);
        httpResponse.addOkHeader(type);
        return httpResponse;
    }

    public static HttpResponse redirect(String url, String httpVersion, int statusCode) {
        HttpResponse httpResponse = new HttpResponse(new HttpStatusLine(httpVersion, statusCode), new byte[0]);
        httpResponse.addFoundHeader(url);
        return httpResponse;
    }

    public static HttpResponse notFound(String httpVersion) {
        return new HttpResponse(new HttpStatusLine(httpVersion, 404), new byte[0]);
    }

    private void addOkHeader(String type) {
        httpHeaders.addContentTypeHeader(type);
        httpHeaders.addContentLengthHeader(this.body.length);
    }

    private void addFoundHeader(String url) {
        httpHeaders.addLocationHeader(url);
    }

    @Override
    public String toString() {
        return httpStatusLine + "\r\n" + httpHeaders + "\r\n" + new String(body);
    }

    public byte[] getBytes() {
        return this.toString().getBytes();
    }
}