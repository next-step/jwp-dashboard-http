package nextstep.jwp.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import nextstep.jwp.model.http.httprequest.HttpRequest;
import nextstep.jwp.model.http.httpresponse.HttpResponse;
import nextstep.jwp.service.ETagService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResourceController extends AbstractController {

    private static final Logger logger = LoggerFactory.getLogger(ResourceController.class);

    private final ETagService etagService;

    public ResourceController(ETagService etagService) {
        this.etagService = etagService;
    }

    @Override
    protected HttpResponse doGet(HttpRequest request) {
        URL resource = getURL(request.getPath());
        String eTag = getETag(resource);
        byte[] body = getBodyFromURL(resource);
        if (hasETagAndIsMatch(request, eTag)) {
            return HttpResponse.notModified(request.getHttpVersion(), body, request.getETag());
        }
        return HttpResponse.ok(request.getPath(), request.getHttpVersion(), body, eTag);
    }

    private String getETag(URL resource) {
        return etagService.getETag(resource.getPath());
    }

    private boolean hasETagAndIsMatch(HttpRequest httpRequest, String eTag) {
        return httpRequest.hasETag() && isMatchETag(httpRequest.getETag(), eTag);
    }

    private boolean isMatchETag(String requestETag, String ETag) {
        return requestETag.equals(ETag);
    }

    private byte[] getBodyFromURL(URL resource) {
        try {
            final Path path = new File(resource.getFile()).toPath();
            return Files.readAllBytes(path);
        } catch (IOException e) {
            logger.info(e.getMessage());
        }
        return new byte[0];
    }

    private URL getURL(String filePath) {
        return Thread.currentThread().getContextClassLoader().getResource("static" + filePath);
    }

}
