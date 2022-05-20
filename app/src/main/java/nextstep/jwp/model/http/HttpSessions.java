package nextstep.jwp.model.http;

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

    public HttpSession getSession(String id) {
        return this.httpSessions.stream()
            .filter(s -> s.isSession(id))
            .findFirst()
            .orElseThrow(() -> new SessionNotFoundException("Session not found with id : " + id));
    }

    public Object getObject(String id, String name) {
        return getSession(id).getAttribute(name);
    }
}
