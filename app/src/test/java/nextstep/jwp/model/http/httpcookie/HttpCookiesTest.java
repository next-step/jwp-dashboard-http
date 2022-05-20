package nextstep.jwp.model.http.httpcookie;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HttpCookiesTest {

    private final String YUMMY_COOKIE = "choco";
    private final String TASTY_COOKIE = "strawberry";
    private final String JSESSIONID = "656cef62-e3c4-40bc-a8df-94732920ed46";

    private final HttpCookies httpCookies = HttpCookies.of("yummy_cookie=" + YUMMY_COOKIE + "; tasty_cookie=" + TASTY_COOKIE + "; JSESSIONID=" + JSESSIONID);

    @Test
    public void create_cookies() {
        assertThat(httpCookies.getCookies().size()).isEqualTo(3);
        assertThat(httpCookies.getCookies().get(0).getValue()).isEqualTo(YUMMY_COOKIE);
        assertThat(httpCookies.getCookies().get(1).getValue()).isEqualTo(TASTY_COOKIE);
        assertThat(httpCookies.getCookies().get(2).getValue()).isEqualTo(JSESSIONID);
    }

    @Test
    public void check_if_has_jsessionid_cookie() {
        assertThat(httpCookies.hasSessionIdCookie()).isTrue();
    }

}