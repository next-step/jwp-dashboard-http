package nextstep.jwp.model.http;

import java.util.Arrays;
import nextstep.jwp.exception.VersionNotFoundException;

public enum HttpVersion {

    HTTP_1_1("HTTP/1.1");

    private String httpVersion;

    HttpVersion(String httpVersion) {
        this.httpVersion = httpVersion;
    }

    public static HttpVersion of(String version) {
        return Arrays.stream(values())
            .filter(httpHeaderType -> httpHeaderType.isEqualHttpVersion(version))
            .findFirst()
            .orElseThrow(() -> new VersionNotFoundException("HTTP Version does not found : " + version));
    }

    public boolean isEqualHttpVersion(String version) {
        return this.httpVersion.equals(version);
    }

    public String getHttpVersion() {
        return httpVersion;
    }

    @Override
    public String toString() {
        return httpVersion;
    }
}
