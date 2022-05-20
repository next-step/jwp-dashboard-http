package nextstep.jwp.model.http;

public class HttpCookie {

    private static final String SESSION_KEY = "JSESSIONID";

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

    public static HttpCookie sessionCookie(String sessionId) {
        return new HttpCookie(SESSION_KEY, sessionId);
    }

    public boolean isCookie(String key) {
        return this.key.equals(key);
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return key + "=" + value;
    }
}
