package nextstep.jwp.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class HttpMethodTest {

    @Test
    public void find_http_method_with_method_name() {
        HttpMethod method = HttpMethod.of("GET");
        assertThat(method).isEqualTo(HttpMethod.GET);
    }

}