package nextstep.jwp.model;

public class HttpStatusLine {

    private HttpVersion httpVersion;
    private HttpStatusCode httpStatusCode;

    public HttpStatusLine(String httpVersion, int statusCode) {
        this.httpVersion = HttpVersion.of(httpVersion);
        this.httpStatusCode = HttpStatusCode.of(statusCode);
    }

    @Override
    public String toString() {
        return httpVersion + " " + httpStatusCode + " ";
    }
}