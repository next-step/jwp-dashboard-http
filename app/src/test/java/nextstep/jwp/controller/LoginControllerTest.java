package nextstep.jwp.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import nextstep.jwp.model.http.httprequest.HttpRequest;
import nextstep.jwp.model.http.httpresponse.HttpResponse;
import org.junit.jupiter.api.Test;

class LoginControllerTest {

    private final Controller controller = new LoginController();

    @Test
    public void post_method_login_response() {
        String[] headers = {"Host: localhost:8080 ", "Connection: keep-alive "};
        HttpRequest request = new HttpRequest("POST /login?account=gugu&password=password HTTP/1.1 ", headers, "account=gugu&password=password");
        HttpResponse response = controller.service(request);
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
        HttpResponse response = controller.service(request);
        String expected = "HTTP/1.1 401 Unauthorized \r\n"
            + "\r\n"
            + "\r\n"
            + "401 UnAuthorized";
        assertThat(response.toString()).isEqualTo(expected);
    }


}