package nextstep.jwp.model.http.httpsession;

public class HttpSessionObject {

    private String name;
    private Object object;

    private HttpSessionObject(String name, Object object) {
        this.name = name;
        this.object = object;
    }

    public static HttpSessionObject of(String name, Object object) {
        return new HttpSessionObject(name, object);
    }

    public boolean isSessionObject(String name) {
        return this.name.equals(name);
    }

    public Object getObject() {
        return object;
    }
}
