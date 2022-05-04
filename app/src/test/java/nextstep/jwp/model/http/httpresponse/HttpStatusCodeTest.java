package nextstep.jwp.model.http.httpresponse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import nextstep.jwp.exception.StatusCodeNotFoundException;
import org.junit.jupiter.api.Test;

class HttpStatusCodeTest {

    private final String STATUS_CODE_NOT_FOUND_EXCEPTION = "Status Code not found with : ";

    @Test
    public void find_status_code_with_code_number() {
        HttpStatusCode code = HttpStatusCode.of(200);
        assertThat(code).isEqualTo(HttpStatusCode.OK);
    }

    @Test
    public void find_status_code_with_invalid_code_number() {
        assertThatThrownBy(() -> HttpStatusCode.of(405))
            .isInstanceOf(StatusCodeNotFoundException.class)
            .hasMessageContaining(STATUS_CODE_NOT_FOUND_EXCEPTION);
    }

}