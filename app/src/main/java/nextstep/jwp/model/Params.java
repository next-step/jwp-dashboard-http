package nextstep.jwp.model;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;
import nextstep.jwp.exception.ParamNotFoundException;

public class Params {

    private Set<Param> params;

    public Params() {
        this.params = new LinkedHashSet<>();
    }

    public static Params of(String query) {
        Params params = new Params();
        String[] parameters = query.split("&");
        for (String param : parameters) {
            int index = param.indexOf("=");
            params.addParam(param.substring(0, index), param.substring(index + 1));
        }
        return params;
    }

    public void addParam(String name, String value) {
        this.params.add(new Param(name, value));
    }

    public String getParam(String name) {
        Param param = this.params.stream()
            .filter(p -> p.isEqualName(name))
            .findFirst()
            .orElseThrow(() -> new ParamNotFoundException("Parameter does not exist."));
        return param.getValue();
    }

    public boolean isEmpty() {
        return this.params.isEmpty();
    }

    @Override
    public String toString() {
        return this.params.stream()
            .map(param -> param.toString())
            .collect(Collectors.joining("&"));
    }

}
