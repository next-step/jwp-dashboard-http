package nextstep.jwp.model;

import java.util.Arrays;
import nextstep.jwp.exception.ContentTypeNotFoundException;

public enum ContentType {

    HTML("html", "text/html;charset=utf-8"),
    CSS("css", "text/css"),
    JS("js", "Application/js");

    private String typeName;
    private String type;

    ContentType(String contentTypeName, String contentType) {
        this.typeName = contentTypeName;
        this.type = contentType;
    }

    public static String contentType(String path) {
        ContentType contentType = Arrays.stream(values())
            .filter(type -> path.endsWith(type.typeName))
            .findFirst()
            .orElseThrow(() -> new ContentTypeNotFoundException("ContentType does not exist : " + path));
        return contentType.getType();
    }

    public String getType() {
        return type;
    }

}
