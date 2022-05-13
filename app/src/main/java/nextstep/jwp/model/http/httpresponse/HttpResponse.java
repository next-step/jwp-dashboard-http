package nextstep.jwp.model.http.httpresponse;

import java.io.File;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
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
        httpHeaders.addContentTypeHeader(type);
        httpHeaders.addContentLengthHeader(this.body.length);
        httpHeaders.addETagHeader(eTag);
    }

    private void addNotModifiedHeader(String eTag) {
        httpHeaders.addETagHeader(eTag);
    }

    private void addFoundHeader(String url) {
        httpHeaders.addLocationHeader(url);
    }

    @Override
    public String toString() {
        return httpStatusLine + "\r\n" + httpHeaders + "\r\n" + new String(body);
    }

    public byte[] toBytes() {
        return this.toString().getBytes(StandardCharsets.UTF_8);
    }

}
