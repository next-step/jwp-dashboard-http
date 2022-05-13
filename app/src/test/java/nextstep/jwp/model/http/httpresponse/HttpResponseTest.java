package nextstep.jwp.model.http.httpresponse;

import nextstep.jwp.model.http.httpresponse.HttpResponse;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class HttpResponseTest {

    @Test
    public void create_ok_response() {
        HttpResponse response = HttpResponse.ok("html", "HTTP/1.1", "nextstep".getBytes(), "abc");
        String expected = "HTTP/1.1 200 OK \r\n"
            + "Content-Type: text/html;charset=utf-8 \r\n"
            + "Content-Length: 8 \r\n"
            + "ETag: abc \r\n"
            + "\r\n"
            + "nextstep";
        assertThat(response.toString()).isEqualTo(expected);
    }

    @Test
    public void create_redirect_response() {
        HttpResponse response = HttpResponse.redirect("/index.html", "HTTP/1.1", 302);
        String expected = "HTTP/1.1 302 Found \r\n"
            + "Location: /index.html \r\n"
            + "\r\n"
            + "";
        assertThat(response.toString()).isEqualTo(expected);
    }

    @Test
    public void create_not_modified_response() {
        HttpResponse response = HttpResponse.notModified("HTTP/1.1", "abc");
        String expected = "HTTP/1.1 304 Not Modified \r\n"
            + "ETag: abc \r\n"
            + "\r\n"
            + "";
        assertThat(response.toString()).isEqualTo(expected);

    }

}