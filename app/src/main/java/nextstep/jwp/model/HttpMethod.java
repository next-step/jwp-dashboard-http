package nextstep.jwp.model;

import java.util.Arrays;
import nextstep.jwp.exception.MethodNotFoundException;

public enum HttpMethod {
    GET, POST, PUT, PATCH, DELETE;

    public static HttpMethod of(String method) {
        return Arrays.stream(values())
            .filter(httpMethod -> httpMethod.name().equals(method))
            .findFirst()
            .orElseThrow(() -> new MethodNotFoundException("HTTP method does not exist : " + method));
    }

    public HttpMethod getMethod() {
        return this;
    }
}
