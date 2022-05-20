package nextstep.jwp.model.http;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import nextstep.jwp.controller.generator.StringGenerator;
import nextstep.jwp.exception.SessionAttributeInvalidException;
import nextstep.jwp.exception.SessionAttributeNotFoundException;

public class HttpSession {

    private final String id;
    private List<HttpSessionObject> sessionObjects = new ArrayList<>();

    public HttpSession(String id) {
        this.id = id;
    }

    public static HttpSession of(StringGenerator stringGenerator) {
        return new HttpSession(stringGenerator.createRandom());
    }

    public void addAttribute(String name, Object object) {
        checkValidate(name, object);
        this.sessionObjects.add(HttpSessionObject.of(name, object));
    }

    public void removeAttribute(String name) {
        this.sessionObjects = this.sessionObjects.stream()
            .filter(s -> !s.isSessionObject(name))
            .collect(Collectors.toList());
    }

    public String getId() {
        return this.id;
    }

    public Object getAttribute(String name) {
        return this.sessionObjects.stream()
            .filter(s -> s.isSessionObject(name))
            .findFirst()
            .orElseThrow(() -> new SessionAttributeNotFoundException("Session value not found named : " + name))
            .getObject();
    }

    private void checkValidate(String name, Object object) {
        if (name.isEmpty() || name.isBlank()) {
            throw new SessionAttributeInvalidException("Session attribute name could not be empty or blank.");
        }

        if (object == null) {
            throw new SessionAttributeInvalidException("Session attribute object could not be null.");
        }
    }

    public boolean isSession(String id) {
        return this.id.equals(id);
    }
}
