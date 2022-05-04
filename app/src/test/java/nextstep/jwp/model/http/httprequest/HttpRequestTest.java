package nextstep.jwp.model;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import nextstep.jwp.model.httprequest.HttpRequest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class HttpRequestTest {

    @Test
    public void create_http_get_request() throws IOException {
        final String expected = String.join("\r\n",
            "GET /index.html HTTP/1.1 ",
            "Host: localhost:8080 ",
            "Connection: keep-alive ",
            "",
            "");
        final InputStream inputStream = new ByteArrayInputStream(expected.getBytes());
        HttpRequest request = new HttpRequest(inputStream);
        assertThat(request.toString()).isEqualTo(expected);
    }

    @Test
    public void create_http_login_request() throws IOException {
        String account = "gugu";
        String password = "password";
        final String expected = String.join("\r\n",
            "GET /login?account=" + account + "&password=" + password + " HTTP/1.1 ",
            "Host: localhost:8080 ",
            "Connection: keep-alive ",
            "",
            "");
        final InputStream inputStream = new ByteArrayInputStream(expected.getBytes());
        HttpRequest request = new HttpRequest(inputStream);
        assertThat(request.toString()).isEqualTo(expected);
        assertThat(request.getParam("account")).isEqualTo(account);
        assertThat(request.getParam("password")).isEqualTo(password);

    }
}