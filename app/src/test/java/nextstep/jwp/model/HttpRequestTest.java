package nextstep.jwp.model;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


class HttpRequestTest {

    @Test
    public void create_http_request() throws IOException {
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
}