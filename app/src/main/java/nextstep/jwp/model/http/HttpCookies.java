package nextstep.jwp.model.http;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import nextstep.jwp.exception.CookieNotFoundException;

public class HttpCookies {

    private static final String SESSION_KEY = "JSESSIONID";

    private List<HttpCookie> cookies;

    private HttpCookies(List<HttpCookie> cookies) {
        this.cookies = cookies;
    }

    public static HttpCookies of(String cookies) {
        return new HttpCookies(generateCookies(cookies));
    }

    private static List<HttpCookie> generateCookies(String cookies) {
        String[] cookiesKeyAndValue = cookies.split("; ");
        return Arrays.stream(cookiesKeyAndValue)
            .map(HttpCookie::of)
            .collect(Collectors.toList());
    }

    public String getSessionId() {
        return getCookie(SESSION_KEY);
    }

    private String getCookie(String key) {
        return this.cookies.stream()
            .filter(c -> c.isCookie(key))
            .findFirst()
            .orElseThrow(() -> new CookieNotFoundException("Cookie does not exist with name : " + key))
            .getValue();
    }


}
