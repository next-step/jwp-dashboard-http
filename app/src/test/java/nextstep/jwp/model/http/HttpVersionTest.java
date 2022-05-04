package nextstep.jwp.model.http;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import nextstep.jwp.exception.VersionNotFoundException;
import org.junit.jupiter.api.Test;

class HttpVersionTest {

    private final String HTTP_VERSION_NOT_FOUND_EXCEPTION = "HTTP Version does not found : ";

    @Test
    public void find_http_version_by_name() {
        HttpVersion version = HttpVersion.of("HTTP/1.1");
        assertThat(version).isEqualTo(HttpVersion.HTTP_1_1);
    }

    @Test
    public void find_http_version_by_invalid_name() {
        assertThatThrownBy(() -> HttpVersion.of("HTTP/3.1"))
            .isInstanceOf(VersionNotFoundException.class)
            .hasMessageContaining(HTTP_VERSION_NOT_FOUND_EXCEPTION);
    }

}