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

    public boolean isGetMethod() {
        return this.requestLine.isGetMethod();
    }

    public String getPath() {
        return this.requestLine.getPath();
    }

    public String getQuery() {
        return this.requestLine.getQuery();
    }

    public String getHttpVersion() {
        return this.requestLine.getHttpVersion();
    }

    @Override
    public String toString() {
        return requestLine + "\r\n" + headers + "\r\n" + body;
    }
}
