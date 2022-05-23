package nextstep.jwp.model.http;

public class Param {

    private String name;
    private String value;

    public Param(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public boolean isEqualName(String name) {
        return this.name.equals(name);
    }

    @Override
    public String toString() {
        return name + '=' + value;
    }
}
