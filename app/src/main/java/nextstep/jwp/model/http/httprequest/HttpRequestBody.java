package nextstep.jwp.model.http.httprequest;

import nextstep.jwp.model.http.Params;

public class HttpRequestBody {

    private Params params;

    public HttpRequestBody() {
        this.params = new Params();
    }

    public HttpRequestBody(String body) {
        this.params = Params.of(body);
    }

    public String getBodyParam(String name){
        return this.params.getParam(name);
    }

    @Override
    public String toString() {
        return params.toString();
    }
}
