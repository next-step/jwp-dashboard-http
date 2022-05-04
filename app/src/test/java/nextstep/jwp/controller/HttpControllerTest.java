package nextstep.jwp.model;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.IOException;
import nextstep.jwp.exception.DuplicatedAccountException;
import nextstep.jwp.model.httprequest.HttpRequest;
import nextstep.jwp.model.httpresponse.HttpResponse;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HttpControllerTest {

    private static final String DUPLICATED_ACCOUNT_EXCEPTION = "This Account already exists. : ";

    private HttpController httpController = new HttpController();


    @Test
    public void get_method_response() throws IOException {
        String[] headers = {"Host: localhost:8080 ", "Connection: keep-alive "};
        HttpRequest request = new HttpRequest("GET static/index.html HTTP/1.1 ", headers, "");
        HttpResponse response = httpController.getResponse(request);
        String expected = "HTTP/1.1 200 OK \r\n"
            + "Content-Type: text/html;charset=utf-8 \r\n"
            + "Content-Length: 12 \r\n"
            + "\r\n"
            + "Hello world!";
        assertThat(response.toString()).isEqualTo(expected);

    }

    @Test
    public void post_method_login_response() {
        String[] headers = {"Host: localhost:8080 ", "Connection: keep-alive "};
        HttpRequest request = new HttpRequest(
            "POST /login?account=gugu&password=password HTTP/1.1 ", headers, "");
        HttpResponse response = httpController.getResponse(request);
        String expected = "HTTP/1.1 302 Found \r\n"
            + "Location: static/index.html \r\n"
            + "\r\n"
            + "";
        assertThat(response.toString()).isEqualTo(expected);
    }

    @Test
    public void post_method_login_with_invalid_password_response() {
        String[] headers = {"Host: localhost:8080 ", "Connection: keep-alive "};
        HttpRequest request = new HttpRequest(
            "POST /login?account=gugu&password=invalidPassword HTTP/1.1 ", headers, "");
        HttpResponse response = httpController.getResponse(request);
        String expected = "HTTP/1.1 401 Unauthorized \r\n"
            + "Location: static/401.html \r\n"
            + "\r\n"
            + "";
        assertThat(response.toString()).isEqualTo(expected);
    }

    @Test
    public void post_method_register_response() {
        String[] headers = {"Host: localhost:8080 ", "Connection: keep-alive "};
        HttpRequest request = new HttpRequest("POST /register HTTP/1.1 ", headers, "account=gugugu&password=password&email=hkkang%40woowahan.com");
        HttpResponse response = httpController.getResponse(request);
        String expected = "HTTP/1.1 302 Found \r\n"
            + "Location: static/index.html \r\n"
            + "\r\n"
            + "";
        assertThat(response.toString()).isEqualTo(expected);
    }

    @Test
    public void post_method_register_with_already_exist_account_is_invalid() {
        String[] headers = {"Host: localhost:8080 ", "Connection: keep-alive "};
        HttpRequest request = new HttpRequest("POST /register HTTP/1.1 ", headers, "account=gugu&password=password&email=hkkang%40woowahan.com");
        assertThatThrownBy(() -> httpController.getResponse(request))
            .isInstanceOf(DuplicatedAccountException.class)
            .hasMessageContaining(DUPLICATED_ACCOUNT_EXCEPTION);
    }

}