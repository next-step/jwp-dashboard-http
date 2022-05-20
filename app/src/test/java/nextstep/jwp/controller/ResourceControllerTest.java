package nextstep.jwp.controller;

import static org.assertj.core.api.Assertions.assertThat;

import nextstep.jwp.MockETagService;
import nextstep.jwp.controller.generator.CertainStringGenerator;
import nextstep.jwp.model.http.httprequest.HttpRequest;
import nextstep.jwp.model.http.httpresponse.HttpResponse;
import nextstep.jwp.model.http.httpsession.HttpSession;
import nextstep.jwp.model.http.httpsession.HttpSessions;
import org.junit.jupiter.api.Test;

class ResourceControllerTest {

    private static final String JSESSIONID = "J_SESSION_ID";
    private final Controller controller = new ResourceController(new MockETagService());

    @Test
    public void get_method_response() {
        String[] headers = {"Host: localhost:8080 ", "Connection: keep-alive "};
        HttpRequest request = new HttpRequest("GET /index.html HTTP/1.1 ", headers, "");
        HttpResponse response = controller.service(request);
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
        String[] headers = {"Host: localhost:8080 ", "Connection: keep-alive ", "If-None-Match: abc"};
        HttpRequest request = new HttpRequest("GET /index.html HTTP/1.1 ", headers, "");
        HttpResponse response = controller.service(request);
        String expected = "HTTP/1.1 304 Not Modified \r\n"
            + "ETag: abc \r\n"
            + "\r\n"
            + "";
        assertThat(response.toString()).isEqualTo(expected);
    }

    @Test
    public void get_method_with_modified_eTag() {
        String[] headers = {"Host: localhost:8080 ", "Connection: keep-alive ", "If-None-Match: def"};
        HttpRequest request = new HttpRequest("GET /index.html HTTP/1.1 ", headers, "");
        HttpResponse response = controller.service(request);
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
        HttpResponse response = controller.service(request);
        String expected = "HTTP/1.1 200 OK \r\n"
            + "Content-Type: text/css \r\n"
            + "Content-Length: 6 \r\n"
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
        String expected = "HTTP/1.1 405 Method Not Allowed \r\n"
            + "\r\n"
            + "\r\n"
            + "";
        assertThat(response.toString()).isEqualTo(expected);
    }

    @Test
    public void get_method_with_login_page_with_sessionId() {
        HttpSessions.of().addSession(new CertainStringGenerator());
        String[] headers = {"Host: localhost:8080 ", "Connection: keep-alive ", "Cookie: yummy_cookie=choco; tasty_cookie=strawberry; JSESSIONID=" + JSESSIONID + " "};
        HttpRequest request = new HttpRequest("GET /login.html HTTP/1.1 ", headers, "");
        HttpResponse response = controller.service(request);
        String expected = "HTTP/1.1 302 Found \r\n"
            + "Location: /index.html \r\n"
            + "\r\n"
            + "";
        assertThat(response.toString()).isEqualTo(expected);
    }

    @Test
    public void get_method_with_login_page_with_not_exist_sessionId_return_page() {
        String[] headers = {"Host: localhost:8080 ", "Connection: keep-alive ", "Cookie: yummy_cookie=choco; tasty_cookie=strawberry; JSESSIONID=" + JSESSIONID + " "};
        HttpRequest request = new HttpRequest("GET /login.html HTTP/1.1 ", headers, "");
        HttpResponse response = controller.service(request);
        String expected = "HTTP/1.1 200 OK \r\n"
            + "Content-Type: text/html;charset=utf-8 \r\n"
            + "Content-Length: 5 \r\n"
            + "ETag: abc \r\n"
            + "\r\n"
            + "Login";
        assertThat(response.toString()).isEqualTo(expected);
    }

}