package nextstep.jwp.controller;

import nextstep.jwp.model.http.httprequest.HttpRequest;
import nextstep.jwp.model.http.httpresponse.HttpResponse;

public interface Controller {

    HttpResponse service(HttpRequest request);

}
