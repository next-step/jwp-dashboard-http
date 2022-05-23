package nextstep.jwp.controller;

import static org.assertj.core.api.Assertions.assertThat;

import nextstep.jwp.mapper.RequestMapper;
import nextstep.jwp.model.http.httprequest.HttpRequest;
import nextstep.jwp.model.http.httpresponse.HttpResponse;
import org.junit.jupiter.api.Test;

class DefaultControllerTest {

    @Test
    public void find_by_invalid_uri() {
        String uri = "/";
        String[] headers = {"Host: localhost:8080 ", "Connection: keep-alive "};
        HttpRequest request = new HttpRequest("GET " + uri + " HTTP/1.1 ", headers, "");
        HttpResponse response = RequestMapper.of(uri).service(request);
        String expected = "HTTP/1.1 404 Not Found \r\n"
            + "\r\n"
            + "\r\n"
            + "404 Not Found";
        assertThat(response.toString()).isEqualTo(expected);
    }

    @Test
    public void post_by_invalid_uri() {
        String uri = "/";
        String[] headers = {"Host: localhost:8080 ", "Connection: keep-alive "};
        HttpRequest request = new HttpRequest("POST " + uri + " HTTP/1.1 ", headers,
            "account=gugu&password=invalidPassword");
        HttpResponse response = RequestMapper.of(uri).service(request);
        String expected = "HTTP/1.1 404 Not Found \r\n"
            + "\r\n"
            + "\r\n"
            + "404 Not Found";
        assertThat(response.toString()).isEqualTo(expected);
    }
}