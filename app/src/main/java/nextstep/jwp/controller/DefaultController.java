package nextstep.jwp.controller;

import nextstep.jwp.model.http.httprequest.HttpRequest;
import nextstep.jwp.model.http.httpresponse.HttpResponse;

public class DefaultController extends AbstractController {

    @Override
    protected HttpResponse doGet(HttpRequest request) {
        return HttpResponse.notFound(request.getHttpVersion());
    }

    @Override
    protected HttpResponse doPost(HttpRequest request) {
        return HttpResponse.notFound(request.getHttpVersion());
    }
}
