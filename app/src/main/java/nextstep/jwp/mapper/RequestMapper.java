package nextstep.jwp.mapper;

import nextstep.jwp.controller.Controller;

public class RequestMapper {

    private static final RequestMaps maps = new RequestMaps();

    public static Controller of(String uri) {
        return maps.getController(uri);
    }
}
