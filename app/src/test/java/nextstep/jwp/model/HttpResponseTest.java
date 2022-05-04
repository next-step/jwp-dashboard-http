package nextstep.jwp.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class HttpResponseTest {

    @Test
    public void create_ok_response() {
        HttpResponse response = HttpResponse.ok("html", "HTTP/1.1", "nextstep".getBytes());
        String expected = "HTTP/1.1 200 OK \r\n"
            + "Content-Type: text/html;charset=utf-8 \r\n"
            + "Content-Length: 8 \r\n"
            + "\r\n"
            + "nextstep";
        assertThat(response.toString()).isEqualTo(expected);
    }

    @Test
    public void create_redirect_response() {
        HttpResponse response = HttpResponse.redirect("static/index.html", "HTTP/1.1", 302);
        String expected = "HTTP/1.1 302 Found \r\n"
            + "Location: static/index.html \r\n"
            + "\r\n"
            + "";
        assertThat(response.toString()).isEqualTo(expected);
    }

}