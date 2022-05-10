package nextstep.jwp.controller;

import nextstep.jwp.model.http.httprequest.HttpRequest;
import nextstep.jwp.model.http.httpresponse.HttpResponse;
import nextstep.jwp.service.UserService;

public class RegisterController extends AbstractController {

    private final UserService userService = new UserService();

    @Override
    protected HttpResponse doPost(HttpRequest request) {
        userService.register(request.getBodyParam("account"),
            request.getBodyParam("password"),
            request.getBodyParam("email"));
        return HttpResponse.redirect("/index.html", request.getHttpVersion(), 302);
    }

}
