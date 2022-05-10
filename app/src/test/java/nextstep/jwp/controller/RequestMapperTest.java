package nextstep.jwp.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


import nextstep.jwp.exception.InvalidUrlException;
import nextstep.jwp.mapper.RequestMapper;
import nextstep.jwp.model.http.httprequest.HttpRequest;
import nextstep.jwp.model.http.httpresponse.HttpResponse;
import org.junit.jupiter.api.Test;

class RequestMapperTest {

    private final String INVALID_URL_EXCEPTION = "This url does not exist. :";


    @Test
    public void find_default_controller_by_uri() {
        String uri = "/index.html";
        assertThat(RequestMapper.of(uri)).isInstanceOf(ResourceController.class);
    }


    @Test
    public void find_controller_by_invalid_uri() {
        String uri = "/test";
        assertThatThrownBy(() -> RequestMapper.of(uri))
            .isInstanceOf(InvalidUrlException.class)
            .hasMessageContaining(INVALID_URL_EXCEPTION);
    }

    @Test
    public void request_with_other_method() {
        String uri = "/login";
        String[] headers = {"Host: localhost:8080 ", "Connection: keep-alive "};
        HttpRequest request = new HttpRequest("PUT " + uri + " HTTP/1.1 ", headers, "account=gugu&password=password&email=hkkang%40woowahan.com");
        HttpResponse response = RequestMapper.of(uri).service(request);
        String expected = "HTTP/1.1 404 Not Found \r\n"
            + "\r\n"
            + "\r\n"
            + "";
        assertThat(response.toString()).isEqualTo(expected);
    }

}