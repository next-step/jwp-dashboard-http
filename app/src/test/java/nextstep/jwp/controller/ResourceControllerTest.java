package nextstep.jwp.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import nextstep.jwp.MockETagService;
import nextstep.jwp.model.http.httprequest.HttpRequest;
import nextstep.jwp.model.http.httpresponse.HttpResponse;
import org.junit.jupiter.api.Test;

class ResourceControllerTest {

    private final Controller controller = new ResourceController(new MockETagService());

    @Test
    public void get_method_response() {
        String[] headers = {"Host: localhost:8080 ", "Connection: keep-alive "};
        HttpRequest request = new HttpRequest("GET /index.html HTTP/1.1 ", headers, "");
        HttpResponse response = controller.service(request);
        String expected = "HTTP/1.1 200 OK \r\n"
            + "Content-Type: text/html;charset=utf-8 \r\n"
            + "Content-Length: 12 \r\n"
            + "Content-Encoding: gzip \r\n"
            + "ETag: abc \r\n"
            + "\r\n"
            + "Hello world!";
        assertThat(response.toString()).isEqualTo(expected);
    }

    @Test
    public void get_method_with_not_modified_eTag() {
        String[] headers = {"Host: localhost:8080 ", "Connection: keep-alive ", "ETag: abc"};
        HttpRequest request = new HttpRequest("GET /index.html HTTP/1.1 ", headers, "");
        HttpResponse response = controller.service(request);
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
        HttpResponse response = controller.service(request);
        String expected = "HTTP/1.1 200 OK \r\n"
            + "Content-Type: text/html;charset=utf-8 \r\n"
            + "Content-Length: 12 \r\n"
            + "Content-Encoding: gzip \r\n"
            + "ETag: abc \r\n"
            + "\r\n"
            + "Hello world!";
        assertThat(response.toString()).isEqualTo(expected);
    }


    @Test
    public void get_method_css_type_response() {
        String[] headers = {"Host: localhost:8080 ", "Connection: keep-alive ", "Accept: text/css,*/*;q=0.1"};
        HttpRequest request = new HttpRequest("GET /css/styles.css HTTP/1.1 ", headers, "");
        HttpResponse response = controller.service(request);
        String expected = "HTTP/1.1 200 OK \r\n"
            + "Content-Type: text/css \r\n"
            + "Content-Length: 6 \r\n"
            + "Content-Encoding: gzip \r\n"
            + "ETag: abc \r\n"
            + "\r\n"
            + "styles";
        assertThat(response.toString()).isEqualTo(expected);
    }

    @Test
    public void request_with_other_method() {
        String[] headers = {"Host: localhost:8080 ", "Connection: keep-alive "};
        HttpRequest request = new HttpRequest("PUT /register HTTP/1.1 ", headers, "account=gugugu&password=password&email=hkkanging%40woowahan.com");
        HttpResponse response = controller.service(request);
        String expected = "HTTP/1.1 404 Not Found \r\n"
            + "\r\n"
            + "\r\n"
            + "";
        assertThat(response.toString()).isEqualTo(expected);
    }

}