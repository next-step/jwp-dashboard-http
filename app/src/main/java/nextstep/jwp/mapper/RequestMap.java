package nextstep.jwp.mapper;

import nextstep.jwp.controller.Controller;
import nextstep.jwp.controller.ResourceController;

public class RequestMap {

    private String uri;
    private Controller controller;

    private RequestMap(String uri, Controller controller) {
        this.uri = uri;
        this.controller = controller;
    }

    public static RequestMap of(String uri, Controller controller) {
        return new RequestMap(uri, controller);
    }

    public boolean isMatchUri(String uri) {
        if (controller.getClass().equals(ResourceController.class)) {
            return uri.startsWith(this.uri) && uri.contains(".");
        }
        return uri.startsWith(this.uri);
    }

    public Controller getController() {
        return controller;
    }
}
