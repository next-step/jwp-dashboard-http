package nextstep.jwp.controller;

import nextstep.jwp.exception.InvalidUrlException;
import nextstep.jwp.exception.MethodNotFoundException;
import nextstep.jwp.model.http.httprequest.HttpRequest;
import nextstep.jwp.model.http.httpresponse.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractController implements Controller {

    private static final Logger logger = LoggerFactory.getLogger(AbstractController.class);

    @Override
    public HttpResponse service(HttpRequest request) {
        try {
            return doService(request);
        } catch (InvalidUrlException e) {
            logger.info(e.getMessage());
            return HttpResponse.notFound(request.getHttpVersion());
        } catch (MethodNotFoundException e) {
            logger.info(e.getMessage());
            return HttpResponse.methodNotAllowed();
        }
    }

    public HttpResponse doService(HttpRequest request) {
        switch (request.getMethod()) {
            case GET:
                return doGet(request);
            case POST:
                return doPost(request);
            case PUT: case DELETE: case PATCH:
                return doOtherMethod();
            default:
                throw new MethodNotFoundException(
                    "HTTP method does not exist : " + request.getMethod());
        }
    }

    protected HttpResponse doGet(HttpRequest request) {
        return HttpResponse.methodNotAllowed();
    }

    protected HttpResponse doPost(HttpRequest request) {
        return HttpResponse.methodNotAllowed();
    }

    protected HttpResponse doOtherMethod() {
        return HttpResponse.methodNotAllowed();
    }
}
