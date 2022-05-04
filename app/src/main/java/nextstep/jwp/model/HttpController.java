package nextstep.jwp.model;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import nextstep.jwp.exception.InvalidPasswordException;
import nextstep.jwp.exception.InvalidUrlException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpController {

    private static final Logger logger = LoggerFactory.getLogger(HttpController.class);

    private final UserService userService;

    public HttpController() {
        userService = new UserService();
    }

    public HttpResponse getResponse(HttpRequest httpRequest) {
        if (httpRequest.isGetRequest()) {
            return doGet(httpRequest);
        }
        if (httpRequest.isPostRequest()) {
            return doPost(httpRequest);
        }
        return doOtherMethod(httpRequest);
    }

    private HttpResponse doGet(HttpRequest httpRequest) {
        byte[] body = getBodyFromPath(httpRequest.getPath());
        return HttpResponse.ok(httpRequest.getPath(), httpRequest.getHttpVersion(), body);
    }

    private HttpResponse doPost(HttpRequest httpRequest) {
        if (httpRequest.isLoginRequest()) {
            return doLogin(httpRequest);
        }
        if (httpRequest.isRegisterRequest()) {
            return doRegister(httpRequest);
        }
        throw new InvalidUrlException("This url does not exist. :" + httpRequest.getPath());
    }

    private HttpResponse doLogin(HttpRequest httpRequest) {
        try {
            userService.login(httpRequest.getParam("account"), httpRequest.getParam("password"));
            return HttpResponse.redirect("static/index.html", httpRequest.getHttpVersion(), 302);
        } catch (InvalidPasswordException e) {
            logger.info(e.getMessage());
            return HttpResponse.redirect("static/401.html", httpRequest.getHttpVersion(), 401);
        }
    }

    private HttpResponse doRegister(HttpRequest httpRequest) {
        userService.register(httpRequest.getBodyParam("account"),
            httpRequest.getBodyParam("password"),
            httpRequest.getBodyParam("email"));
        return HttpResponse.redirect("static/index.html", httpRequest.getHttpVersion(), 302);
    }

    private HttpResponse doOtherMethod(HttpRequest httpRequest) {
        return HttpResponse.notFound(httpRequest.getHttpVersion());
    }

    private byte[] getBodyFromPath(String filePath) {
        try {
            URL resource = getClass().getClassLoader().getResource(filePath);
            final Path path = new File(resource.getPath()).toPath();
            return Files.readAllBytes(path);
        } catch (IOException e) {
            logger.info(e.getMessage());
        }
        return new byte[0];
    }

}
