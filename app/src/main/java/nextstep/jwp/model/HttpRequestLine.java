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

    public boolean isGetMethod() {
        return this.httpMethod.equals(HttpMethod.GET);
    }

    public String getPath() {
        return this.httpUrl.getPath();
    }

    public String getQuery() {
        return this.httpUrl.getQuery();
    }

    public String getHttpVersion() {
        return this.httpVersion.getHttpVersion();
    }

    @Override
    public String toString() {
        return httpMethod + " " + httpUrl + " " + httpVersion + " ";
    }
}
