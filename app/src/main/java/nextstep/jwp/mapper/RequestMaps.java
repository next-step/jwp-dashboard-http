package nextstep.jwp.mapper;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import nextstep.jwp.controller.Controller;
import nextstep.jwp.controller.DefaultController;
import nextstep.jwp.controller.LoginController;
import nextstep.jwp.controller.generator.RandomStringGenerator;
import nextstep.jwp.controller.RegisterController;
import nextstep.jwp.controller.ResourceController;
import nextstep.jwp.exception.InvalidUrlException;
import nextstep.jwp.service.ETagService;

public class RequestMaps {

    private static final Set<RequestMap> maps;

    static {
        maps = new LinkedHashSet<>(Arrays.asList(
            RequestMap.of("/", new ResourceController(new ETagService())),
            RequestMap.of("/login", new LoginController(new RandomStringGenerator())),
            RequestMap.of("/register", new RegisterController()),
            RequestMap.of("", new DefaultController())));
    }

    public RequestMaps() {
    }

    public Controller getController(String uri) {
        return this.maps.stream()
            .filter(map -> map.isMatchUri(uri))
            .findFirst()
            .orElseThrow(() -> new InvalidUrlException("This url does not exist. :" + uri))
            .getController();
    }

}
