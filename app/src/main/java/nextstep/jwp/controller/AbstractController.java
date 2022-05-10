package nextstep.jwp.controller;

import nextstep.jwp.exception.MethodNotFoundException;
import nextstep.jwp.model.http.httprequest.HttpRequest;
import nextstep.jwp.model.http.httpresponse.HttpResponse;

public abstract class AbstractController implements Controller {

    @Override
    public HttpResponse service(HttpRequest request) {
        switch (request.getMethod()) {
            case GET:
                return doGet(request);
            case POST:
                return doPost(request);
            case PUT: case DELETE: case PATCH:
                return doOtherMethod(request);
            default:
                throw new MethodNotFoundException(
                    "HTTP method does not exist : " + request.getMethod());
        }
    }

    protected HttpResponse doGet(HttpRequest request) {
        return HttpResponse.none();
    }

    protected HttpResponse doPost(HttpRequest request) {
        return HttpResponse.none();
    }

    protected HttpResponse doOtherMethod(HttpRequest request) {
        return HttpResponse.notFound(request.getHttpVersion());
    }
}
