package nextstep.jwp.model;

public class HttpUrl {

    private final String path;
    private final String query;

    private HttpUrl(String path, String query) {
        this.path = path;
        this.query = query;
    }

    private HttpUrl(String path) {
        this.path = path;
        this.query = "";
    }

    public static HttpUrl of(String uri) {
        int index = uri.indexOf("?");
        if (index != -1) {
            return new HttpUrl(uri.substring(0, index), uri.substring(index + 1));
        }
        return new HttpUrl(uri);
    }

    public String getPath() {
        return path;
    }

    public String getQuery() {
        return query;
    }

    @Override
    public String toString() {
        if (query.isEmpty()) {
            return path;
        }
        return path + "?" +
            query;
    }
}
