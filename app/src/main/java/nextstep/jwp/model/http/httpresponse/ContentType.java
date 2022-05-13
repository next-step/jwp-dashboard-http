package nextstep.jwp.model.http.httpresponse;

import java.util.Arrays;
import nextstep.jwp.exception.ContentTypeNotFoundException;

public enum ContentType {

    HTML("html", "text/html;charset=utf-8"),
    CSS("css", "text/css"),
    JS("js", "Application/js"),
    JPEG("jpeg", "image/jpeg"),
    PNG("png", "image/png"),
    GIF("gif", "image/gif"),
    SVG("svg", "image/svg+xml");

    private String typeName;
    private String type;

    ContentType(String contentTypeName, String contentType) {
        this.typeName = contentTypeName;
        this.type = contentType;
    }

    public static String contentType(String path) {
        ContentType contentType = Arrays.stream(values())
            .filter(type -> type.isEqualType(path))
            .findFirst()
            .orElseThrow(() -> new ContentTypeNotFoundException("ContentType does not exist : " + path));
        return contentType.getType();
    }

    private boolean isEqualType(String path) {
        String[] parameters = path.split("\\.");
        return parameters[parameters.length - 1].equals(this.typeName);
    }

    public String getType() {
        return type;
    }

}
