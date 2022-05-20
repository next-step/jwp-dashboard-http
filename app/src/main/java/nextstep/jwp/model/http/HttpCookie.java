package nextstep.jwp.model.http;

public class HttpCookie {

    private String key;
    private String value;

    private HttpCookie(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public static HttpCookie of(String cookie) {
        String[] cookieKeyAndValue = cookie.split("=");
        return new HttpCookie(cookieKeyAndValue[0], cookieKeyAndValue[1]);
    }

    public boolean isCookie(String key) {
        return this.key.equals(key);
    }

    public String getValue() {
        return value;
    }
}
