package nextstep.jwp.model.http.httprequest;

import nextstep.jwp.model.http.HttpVersion;

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

    public String getPath() {
        return this.httpUrl.getPath();
    }

    public String getParam(String name) {
        return this.httpUrl.getParam(name);
    }

    public String getHttpVersion() {
        return this.httpVersion.getHttpVersion();
    }

    public HttpMethod getHttpMethod() {
        return this.httpMethod;
    }

    @Override
    public String toString() {
        return httpMethod + " " + httpUrl + " " + httpVersion + " ";
    }
}
