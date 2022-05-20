package nextstep.jwp.model.http.httpcookie;

import java.util.Arrays;
import java.util.Collections;
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

    public boolean hasSessionIdCookie() {
        return this.cookies.stream()
            .anyMatch(c -> c.isCookie(SESSION_KEY));
    }

    public List<HttpCookie> getCookies() {
        return Collections.unmodifiableList(cookies);
    }

    public String getSessionId() {
        return cookies.stream()
            .filter(c -> c.isCookie(SESSION_KEY))
            .findFirst()
            .orElseThrow(() -> new CookieNotFoundException("Cookie SessionId does not exist in request headers"))
            .getValue();
    }
}
