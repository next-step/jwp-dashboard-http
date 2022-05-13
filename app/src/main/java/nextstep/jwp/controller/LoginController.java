package nextstep.jwp.controller;

import nextstep.jwp.exception.InvalidPasswordException;
import nextstep.jwp.model.http.httprequest.HttpRequest;
import nextstep.jwp.model.http.httpresponse.HttpResponse;
import nextstep.jwp.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginController extends AbstractController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    private final UserService userService = new UserService();

    @Override
    protected HttpResponse doPost(HttpRequest request) {
        try {
            userService.login(request.getBodyParam("account"), request.getBodyParam("password"));
            return HttpResponse.redirect("/index.html", request.getHttpVersion(), 302);
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
