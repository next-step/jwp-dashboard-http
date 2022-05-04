package nextstep.jwp.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


class HttpResponseTest {

    @Test
    public void createResponse() {
        HttpResponse response= new HttpResponse("html", "HTTP/1.1", 200, "nextstep".getBytes());
        String expected = "HTTP/1.1 200 OK \r\n"
            + "Content-Type: text/html;charset=utf-8 \r\n"
            + "Content-Length: 8 \r\n"
            + "\r\n"
            + "nextstep";
        assertThat(response.toString()).isEqualTo(expected);
    }

}