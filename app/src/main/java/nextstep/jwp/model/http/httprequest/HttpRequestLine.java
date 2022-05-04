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

    public boolean isGetRequest() {
        return this.httpMethod.equals(HttpMethod.GET);
    }

    public boolean isPostRequest() {
        return this.httpMethod.equals(HttpMethod.POST);
    }

    public boolean isLoginRequest() {
        return this.httpUrl.isLoginRequest();
    }

    public boolean isRegisterRequest() {
        return this.httpUrl.isRegisterRequest();
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

    @Override
    public String toString() {
        return httpMethod + " " + httpUrl + " " + httpVersion + " ";
    }
}
