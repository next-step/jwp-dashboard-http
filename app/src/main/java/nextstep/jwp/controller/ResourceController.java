package nextstep.jwp.controller;

import java.net.URL;
import nextstep.jwp.model.http.httprequest.HttpRequest;
import nextstep.jwp.model.http.httpresponse.HttpResponse;
import nextstep.jwp.service.ETagService;
import nextstep.jwp.service.FileService;
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
        URL resource = FileService.getURL(request.getPath());
        String eTag = getETag(resource);
        byte[] body = FileService.getBodyFromURL(resource);
        if (hasIfNoneMatchAndIsMatch(request, eTag)) {
            return HttpResponse.notModified(request.getHttpVersion(), request.getIfNoneMatch());
        }
        return HttpResponse.ok(request.getPath(), request.getHttpVersion(), body, eTag);
    }

    private String getETag(URL resource) {
        return etagService.getETag(resource.getPath());
    }

    private boolean hasIfNoneMatchAndIsMatch(HttpRequest httpRequest, String eTag) {
        return httpRequest.hasIfNoneMatch() && isMatchETag(httpRequest.getIfNoneMatch(), eTag);
    }

    private boolean isMatchETag(String ifNoneMatch, String ETag) {
        return ifNoneMatch.equals(ETag);
    }

}
