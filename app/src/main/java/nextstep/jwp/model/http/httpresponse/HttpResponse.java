package nextstep.jwp.model.http.httpresponse;

import java.nio.charset.StandardCharsets;
import nextstep.jwp.model.http.HttpHeaderType;
import nextstep.jwp.model.http.HttpHeaders;
import nextstep.jwp.service.FileService;

public class HttpResponse {

    private HttpStatusLine httpStatusLine;
    private HttpHeaders httpHeaders = new HttpHeaders();
    private byte[] body;

    public HttpResponse(HttpStatusLine httpStatusLine, byte[] body) {
        this.httpStatusLine = httpStatusLine;
        this.body = body;
    }

    public static HttpResponse ok(String type, String httpVersion, byte[] body, String eTag) {
        HttpResponse httpResponse = new HttpResponse(new HttpStatusLine(httpVersion, 200), body);
        httpResponse.addOkHeader(type, eTag);
        return httpResponse;
    }

    public static HttpResponse notModified(String httpVersion, String eTag) {
        HttpResponse httpResponse = new HttpResponse(new HttpStatusLine(httpVersion, 304), new byte[0]);
        httpResponse.addNotModifiedHeader(eTag);
        return httpResponse;
    }

    public static HttpResponse redirectWithSessionId(String url, String httpVersion, int statusCode, String sessionCookie) {
        HttpResponse httpResponse = redirect(url, httpVersion, statusCode);
        httpResponse.addCookieHeader(sessionCookie);
        return httpResponse;
    }

    public static HttpResponse redirect(String url, String httpVersion, int statusCode) {
        HttpResponse httpResponse = new HttpResponse(new HttpStatusLine(httpVersion, statusCode), new byte[0]);
        httpResponse.addFoundHeader(url);
        return httpResponse;
    }

    public static HttpResponse unAuthorized(String httpVersion) {
        HttpResponse httpResponse = new HttpResponse(new HttpStatusLine(httpVersion, 401), FileService.getBodyFromPath("/401.html"));
        return httpResponse;
    }

    public static HttpResponse notFound(String httpVersion) {
        return new HttpResponse(new HttpStatusLine(httpVersion, 404), FileService.getBodyFromPath("/404.html"));
    }

    public static HttpResponse methodNotAllowed() {
        return new HttpResponse(new HttpStatusLine("HTTP/1.1", 405), new byte[0]);
    }

    private void addOkHeader(String type, String eTag) {
        httpHeaders.addTypeHeader(HttpHeaderType.CONTENT_TYPE, ContentType.contentType(type));
        httpHeaders.addTypeHeader(HttpHeaderType.CONTENT_LENGTH, String.valueOf(this.body.length));
        httpHeaders.addTypeHeader(HttpHeaderType.E_TAG, eTag);
    }

    private void addNotModifiedHeader(String eTag) {
        httpHeaders.addTypeHeader(HttpHeaderType.E_TAG, eTag);
    }

    private void addFoundHeader(String url) {
        httpHeaders.addTypeHeader(HttpHeaderType.LOCATION, url);
    }

    private void addCookieHeader(String sessionCookie) {
        httpHeaders.addTypeHeader(HttpHeaderType.SET_COOKIE, sessionCookie);
    }

    @Override
    public String toString() {
        return httpStatusLine + "\r\n" + httpHeaders + "\r\n" + new String(body);
    }

    public byte[] toBytes() {
        return this.toString().getBytes(StandardCharsets.UTF_8);
    }

}
