package nextstep.jwp.model.http.httpsession;

import java.util.ArrayList;
import java.util.List;
import nextstep.jwp.controller.generator.StringGenerator;
import nextstep.jwp.exception.SessionNotFoundException;

public class HttpSessions {

    private List<HttpSession> httpSessions = new ArrayList<>();

    private HttpSessions() {
    }

    public static HttpSessions of() {
        return InClassInstance.instance;
    }

    private static class InClassInstance {

        private static final HttpSessions instance = new HttpSessions();
    }

    public HttpSession addSession(StringGenerator stringGenerator) {
        HttpSession httpSession = HttpSession.of(stringGenerator);
        this.httpSessions.add(httpSession);
        return httpSession;
    }

    public boolean hasSession(String sessionId) {
        return httpSessions.stream()
            .anyMatch(h -> h.isSession(sessionId));
    }

}
