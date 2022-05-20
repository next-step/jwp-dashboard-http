package nextstep.jwp.model.http.httpsession;

import static org.junit.jupiter.api.Assertions.*;

import nextstep.jwp.controller.generator.CertainStringGenerator;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class HttpSessionsTest {


    @Test
    public void check_http_session_is_single_ton() {
        assertThat(HttpSessions.of()).isEqualTo(HttpSessions.of());
    }

    @Test
    public void check_has_sessionId() {
        // given
        HttpSession httpSession = HttpSessions.of().addSession(new CertainStringGenerator());
        assertThat(HttpSessions.of().hasSession(httpSession.getId())).isTrue();
    }

}