package nextstep.jwp.controller;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.IOException;
import nextstep.jwp.MockETagService;
import nextstep.jwp.exception.DuplicatedAccountException;
import nextstep.jwp.exception.InvalidUrlException;
import nextstep.jwp.model.http.httprequest.HttpRequest;
import nextstep.jwp.model.http.httpresponse.HttpResponse;
import nextstep.jwp.service.UserService;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HttpControllerTest {

    private final String DUPLICATED_ACCOUNT_EXCEPTION = "This Account already exists. : ";
    private final String INVALID_URL_EXCEPTION = "This url does not exist. :";

    private HttpController httpController = new HttpController(new UserService(), new MockETagService());

    @Test
    public void get_method_response() {
        String[] headers = {"Host: localhost:8080 ", "Connection: keep-alive "};
        HttpRequest request = new HttpRequest("GET /index.html HTTP/1.1 ", headers, "");
        HttpResponse response = httpController.getResponse(request);
        String expected = "HTTP/1.1 200 OK \r\n"
            + "Content-Type: text/html;charset=utf-8 \r\n"
            + "Content-Length: 12 \r\n"
            + "ETag: abc \r\n"
            + "\r\n"
            + "Hello world!";
        assertThat(response.toString()).isEqualTo(expected);
    }

    @Test
    public void get_method_with_not_modified_eTag() {
        String[] headers = {"Host: localhost:8080 ", "Connection: keep-alive ", "ETag: abc"};
        HttpRequest request = new HttpRequest("GET /index.html HTTP/1.1 ", headers, "");
        HttpResponse response = httpController.getResponse(request);
        String expected = "HTTP/1.1 304 Not Modified \r\n"
            + "ETag: abc \r\n"
            + "\r\n"
            + "Hello world!";
        assertThat(response.toString()).isEqualTo(expected);
    }

    @Test
    public void get_method_with_modified_eTag() {
        String[] headers = {"Host: localhost:8080 ", "Connection: keep-alive ", "ETag: def"};
        HttpRequest request = new HttpRequest("GET /index.html HTTP/1.1 ", headers, "");
        HttpResponse response = httpController.getResponse(request);
        String expected = "HTTP/1.1 200 OK \r\n"
            + "Content-Type: text/html;charset=utf-8 \r\n"
            + "Content-Length: 12 \r\n"
            + "ETag: abc \r\n"
            + "\r\n"
            + "Hello world!";
        assertThat(response.toString()).isEqualTo(expected);
    }


    @Test
    public void get_method_css_type_response() {
        String[] headers = {"Host: localhost:8080 ", "Connection: keep-alive ", "Accept: text/css,*/*;q=0.1"};
        HttpRequest request = new HttpRequest("GET /css/styles.css HTTP/1.1 ", headers, "");
        HttpResponse response = httpController.getResponse(request);
        String expected = "HTTP/1.1 200 OK \r\n"
            + "Content-Type: text/css \r\n"
            + "Content-Length: 6 \r\n"
            + "ETag: abc \r\n"
            + "\r\n"
            + "styles";
        assertThat(response.toString()).isEqualTo(expected);
    }

    @Test
    public void post_method_login_response() {
        String[] headers = {"Host: localhost:8080 ", "Connection: keep-alive "};
        HttpRequest request = new HttpRequest("POST /login?account=gugu&password=password HTTP/1.1 ", headers, "account=gugu&password=password");
        HttpResponse response = httpController.getResponse(request);
        String expected = "HTTP/1.1 302 Found \r\n"
            + "Location: /index.html \r\n"
            + "\r\n"
            + "";
        assertThat(response.toString()).isEqualTo(expected);
    }

    @Test
    public void post_method_login_with_invalid_password_response() {
        String[] headers = {"Host: localhost:8080 ", "Connection: keep-alive "};
        HttpRequest request = new HttpRequest("POST /login?account=gugu&password=invalidPassword HTTP/1.1 ", headers, "account=gugu&password=invalidPassword");
        HttpResponse response = httpController.getResponse(request);
        String expected = "HTTP/1.1 401 Unauthorized \r\n"
            + "Location: /401.html \r\n"
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
            + "Location: /index.html \r\n"
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

    @Test
    public void post_method_with_invalid_url() {
        String[] headers = {"Host: localhost:8080 ", "Connection: keep-alive "};
        HttpRequest request = new HttpRequest("POST /test HTTP/1.1 ", headers, "account=gugu&password=password&email=hkkang%40woowahan.com");
        assertThatThrownBy(() -> httpController.getResponse(request))
            .isInstanceOf(InvalidUrlException.class)
            .hasMessageContaining(INVALID_URL_EXCEPTION);
    }

    @Test
    public void request_with_other_method() {
        String[] headers = {"Host: localhost:8080 ", "Connection: keep-alive "};
        HttpRequest request = new HttpRequest("PUT /register HTTP/1.1 ", headers, "account=gugugu&password=password&email=hkkanging%40woowahan.com");
        HttpResponse response = httpController.getResponse(request);
        String expected = "HTTP/1.1 404 Not Found \r\n"
            + "\r\n"
            + "\r\n"
            + "";
        assertThat(response.toString()).isEqualTo(expected);
    }


}