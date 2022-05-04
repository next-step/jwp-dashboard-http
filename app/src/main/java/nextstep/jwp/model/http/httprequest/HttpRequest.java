package nextstep.jwp.model.http.httprequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import nextstep.jwp.exception.HttpRequestMessageIsEmptyException;
import nextstep.jwp.model.http.HttpHeaders;

public class HttpRequest {

    private final HttpRequestLine requestLine;
    private final HttpHeaders headers;
    private final HttpRequestBody body;

    public HttpRequest(InputStream inputStream) throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        this.requestLine = readRequestLine(bufferedReader);
        this.headers = readHeaders(bufferedReader);
        this.body = readBody(bufferedReader);
    }

    public HttpRequest(String requestLine, String[] headers, String body) {
        this.requestLine = new HttpRequestLine(requestLine);
        this.headers = new HttpHeaders(headers);
        this.body = new HttpRequestBody(body);
    }

    private HttpRequestLine readRequestLine(BufferedReader bufferedReader) throws IOException {
        String line = bufferedReader.readLine();
        if (line == null) {
            throw new HttpRequestMessageIsEmptyException("HttpRequestMessage is empty.");
        }
        return new HttpRequestLine(line);
    }

    private HttpHeaders readHeaders(BufferedReader bufferedReader) throws IOException {
        HttpHeaders httpHeaders = new HttpHeaders();
        String line = bufferedReader.readLine();
        while ((line != null) && (!"".equals(line))) {
            httpHeaders.addHeader(line);
            line = bufferedReader.readLine();
        }
        return httpHeaders;
    }

    private HttpRequestBody readBody(BufferedReader bufferedReader) throws IOException {
        String body = "";
        String line = bufferedReader.readLine();
        while (line != null) {
            body += (line + "\n");
        }
        return new HttpRequestBody(body);
    }

    public boolean isGetRequest() {
        return this.requestLine.isGetRequest();
    }

    public boolean isPostRequest() {
        return this.requestLine.isPostRequest();
    }

    public boolean isLoginRequest() {
        return this.requestLine.isLoginRequest();
    }

    public boolean isRegisterRequest() {
        return this.requestLine.isRegisterRequest();
    }

    public String getPath() {
        return this.requestLine.getPath();
    }

    public String getParam(String name) {
        return this.requestLine.getParam(name);
    }

    public String getBodyParam(String name) {
        return this.body.getBodyParam(name);
    }

    public String getHttpVersion() {
        return this.requestLine.getHttpVersion();
    }

    @Override
    public String toString() {
        return requestLine + "\r\n" + headers + "\r\n" + body;
    }
}