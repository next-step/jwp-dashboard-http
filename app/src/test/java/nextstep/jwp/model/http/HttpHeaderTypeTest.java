package nextstep.jwp.model.http;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import nextstep.jwp.exception.HeaderNotFoundException;
import nextstep.jwp.model.http.httpheader.HttpHeaderType;
import org.junit.jupiter.api.Test;

class HttpHeaderTypeTest {

    private final String HEADER_NOT_FOUND_EXCEPTION = "Header Type does not exist : ";

    @Test
    public void find_header_by_type() {
        HttpHeaderType headerType = HttpHeaderType.of("Host");
        assertThat(headerType).isEqualTo(HttpHeaderType.HOST);
    }

    @Test
    public void find_header_by_invalid_type() {
        assertThatThrownBy(() -> HttpHeaderType.of("Test"))
            .isInstanceOf(HeaderNotFoundException.class)
            .hasMessageContaining(HEADER_NOT_FOUND_EXCEPTION);
    }

}