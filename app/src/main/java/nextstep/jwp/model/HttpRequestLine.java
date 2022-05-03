package nextstep.jwp.model;

public class HttpRequestLine {

    private HttpMethod httpMethod;
    private HttpUrl httpUrl;
    private HttpVersion httpVersion;

    public HttpRequestLine(String requestLine) {
        String[] request = requestLine.split(" ");
        this.httpMethod = HttpMethod.of(request[0]);
        this.httpUrl = HttpUrl.of(request[1]);
        this.httpVersion = HttpVersion.of(request[2]);
    }
}
