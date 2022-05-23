package nextstep.jwp.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import nextstep.jwp.exception.DuplicatedAccountException;
import nextstep.jwp.model.http.httprequest.HttpRequest;
import nextstep.jwp.model.http.httpresponse.HttpResponse;
import org.junit.jupiter.api.Test;

class RegisterControllerTest {

    private final String DUPLICATED_ACCOUNT_EXCEPTION = "This Account already exists. : ";

    private final Controller controller = new RegisterController();

    @Test
    public void post_method_register_response() {
        String[] headers = {"Host: localhost:8080 ", "Connection: keep-alive "};
        HttpRequest request = new HttpRequest("POST /register HTTP/1.1 ", headers, "account=gugugu&password=password&email=hkkang%40woowahan.com");
        HttpResponse response = controller.service(request);
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
        assertThatThrownBy(() -> controller.service(request))
            .isInstanceOf(DuplicatedAccountException.class)
            .hasMessageContaining(DUPLICATED_ACCOUNT_EXCEPTION);
    }

}