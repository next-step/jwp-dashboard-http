package nextstep.jwp.controller;

import static org.assertj.core.api.Assertions.assertThat;

import nextstep.jwp.mapper.RequestMapper;
import nextstep.jwp.model.http.httprequest.HttpRequest;
import nextstep.jwp.model.http.httpresponse.HttpResponse;
import org.junit.jupiter.api.Test;

class RequestMapperTest {

    @Test
    public void find_default_controller_by_uri() {
        String uri = "/index.html";
        assertThat(RequestMapper.of(uri)).isInstanceOf(ResourceController.class);
    }


    @Test
    public void find_controller_by_invalid_uri() {
        String uri = "/test";
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
    public void request_with_other_method() {
        String uri = "/login";
        String[] headers = {"Host: localhost:8080 ", "Connection: keep-alive "};
        HttpRequest request = new HttpRequest("PUT " + uri + " HTTP/1.1 ", headers, "account=gugu&password=password&email=hkkang%40woowahan.com");
        HttpResponse response = RequestMapper.of(uri).service(request);
        String expected = "HTTP/1.1 405 Method Not Allowed \r\n"
            + "\r\n"
            + "\r\n"
            + "";
        assertThat(response.toString()).isEqualTo(expected);
    }

}