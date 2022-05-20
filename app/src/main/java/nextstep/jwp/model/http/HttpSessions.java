package nextstep.jwp.model.http;

import java.util.ArrayList;
import java.util.List;
import nextstep.jwp.exception.SessionNotFoundException;
import nextstep.jwp.model.User;

public class HttpSessions {

    private List<HttpSession> httpSessions = new ArrayList<>();

    private HttpSessions() {
    }

    public static HttpSessions of() {
        return new HttpSessions();
    }

    public void addSession() {
        this.httpSessions.add(HttpSession.of());
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
