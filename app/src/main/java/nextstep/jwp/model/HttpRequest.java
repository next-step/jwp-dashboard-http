package nextstep.jwp.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import nextstep.jwp.exception.HttpRequestMessageIsEmptyException;

public class HttpRequest {

    private final HttpRequestLine requestLine;
    private final HttpHeaders headers;
    private final String body;

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
        this.body = body;
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

    private String readBody(BufferedReader bufferedReader) throws IOException {
        String body = "";
        String line = bufferedReader.readLine();
        while (line != null) {
            body += (line + "\n");
        }
        return body;
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

    public String getParams() {
        return this.requestLine.getParams();
    }

    public String getParam(String name) {
        return this.requestLine.getParam(name);
    }

    public String getHttpVersion() {
        return this.requestLine.getHttpVersion();
    }

    @Override
    public String toString() {
        return requestLine + "\r\n" + headers + "\r\n" + body;
    }
}
