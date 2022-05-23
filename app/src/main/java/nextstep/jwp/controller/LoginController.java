package nextstep.jwp.controller;

import nextstep.jwp.controller.generator.StringGenerator;
import nextstep.jwp.exception.InvalidPasswordException;
import nextstep.jwp.model.http.httprequest.HttpRequest;
import nextstep.jwp.model.http.httpresponse.HttpResponse;
import nextstep.jwp.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginController extends AbstractController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    private UserService userService;

    public LoginController(StringGenerator stringGenerator) {
        this.userService = new UserService(stringGenerator);
    }

    @Override
    protected HttpResponse doPost(HttpRequest request) {
        try {
            String sessionCookie = userService.login(request.getBodyParam("account"), request.getBodyParam("password"));
            return HttpResponse.redirectWithSessionId("/index.html", request.getHttpVersion(), 302, sessionCookie);
        } catch (InvalidPasswordException e) {
            logger.info(e.getMessage());
            return HttpResponse.unAuthorized(request.getHttpVersion());
        }
    }

    @Override
    protected HttpResponse doGet(HttpRequest request) {
        return HttpResponse.notFound(request.getHttpVersion());
    }
}
