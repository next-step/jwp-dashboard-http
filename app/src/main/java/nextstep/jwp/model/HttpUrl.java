package nextstep.jwp.model;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class HttpUrl {

    private final String path;
    private final Params params;

    private HttpUrl(String path, Params params) {
        this.path = path;
        this.params = params;
    }

    private HttpUrl(String path) {
        this.path = path;
        this.params = new Params();
    }

    public static HttpUrl of(String uri) {
        int index = uri.indexOf("?");
        if (index != -1) {
            return new HttpUrl(uri.substring(0, index), Params.of(uri.substring(index + 1)));
        }
        return new HttpUrl(uri);
    }

    public String getPath() {
        return path;
    }

    public String getParams() {
        return this.params.toString();
    }

    public String getParam(String name) {
        return this.params.getParam(name);
    }

    public boolean isLoginRequest() {
        return this.path.endsWith("login");
    }

    public boolean isRegisterRequest() {
        return this.path.endsWith("register");
    }

    @Override
    public String toString() {
        if (params.isEmpty()) {
            return path;
        }
        return path + "?" +
            params;
    }
}
