package nextstep.jwp.model.http.httprequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import nextstep.jwp.exception.MethodNotFoundException;
import org.junit.jupiter.api.Test;

class HttpMethodTest {

    private final String METHOD_NOT_FOUND_EXCEPTION = "HTTP method does not exist : ";


    @Test
    public void find_http_method_with_method_name() {
        HttpMethod method = HttpMethod.of("GET");
        assertThat(method).isEqualTo(HttpMethod.GET);
    }

    @Test
    public void find_http_method_with_invalid_method_name() {
        assertThatThrownBy(() -> HttpMethod.of("TEST"))
            .isInstanceOf(MethodNotFoundException.class)
            .hasMessageContaining(METHOD_NOT_FOUND_EXCEPTION);
    }

}