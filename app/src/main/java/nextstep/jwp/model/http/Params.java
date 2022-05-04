package nextstep.jwp.model.http;

import java.security.InvalidParameterException;
import java.util.Collections;
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
        if (!query.isEmpty()) {
            String[] parameters = query.split("&");
            addParams(params, parameters);
        }
        return params;
    }

    private static void addParams(Params params, String[] parameters) {
        for (String param : parameters) {
            int index = param.indexOf("=");
            checkIndex(index);
            params.addParam(param.substring(0, index), param.substring(index + 1));
        }
    }

    private static void checkIndex(int index) {
        if (index == -1) {
            throw new InvalidParameterException("This parameter is not invalid.");
        }
    }

    public void addParam(String name, String value) {
        this.params.add(new Param(name, value.replace("\n", "")));
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

    public Set<Param> getParams() {
        return Collections.unmodifiableSet(params);
    }

    @Override
    public String toString() {
        return this.params.stream()
            .map(param -> param.toString())
            .collect(Collectors.joining("&"));
    }

}
