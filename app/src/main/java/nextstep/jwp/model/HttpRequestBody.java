package nextstep.jwp.model;

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

}
