package nextstep.jwp.model;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpController {

    private static final Logger logger = LoggerFactory.getLogger(HttpController.class);

    public HttpResponse getResponse(HttpRequest httpRequest) {
        if (httpRequest.isGetMethod()) {
            return doGet(httpRequest);
        }
        return doPost(httpRequest);
    }

    private HttpResponse doGet(HttpRequest httpRequest) {
        byte[] body = getBodyFromPath(httpRequest.getPath());
        return new HttpResponse(httpRequest.getPath(), httpRequest.getHttpVersion(), 200, body);
    }

    private HttpResponse doPost(HttpRequest httpRequest) {
        return null;
    }

    private byte[] getBodyFromPath(String filePath) {
        try {
            URL resource = getClass().getClassLoader().getResource(filePath);
            final Path path = new File(resource.getPath()).toPath();
            return Files.readAllBytes(path);
        } catch (IOException e) {
            logger.info(e.getMessage());
        }
        return new byte[0];
    }

}
