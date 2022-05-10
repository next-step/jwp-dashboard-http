package nextstep.jwp.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import nextstep.jwp.exception.InvalidPasswordException;
import nextstep.jwp.exception.InvalidUrlException;
import nextstep.jwp.service.ETagService;
import nextstep.jwp.service.UserService;
import nextstep.jwp.model.http.httprequest.HttpRequest;
import nextstep.jwp.model.http.httpresponse.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpController {

    private static final Logger logger = LoggerFactory.getLogger(HttpController.class);

    private UserService userService;
    private ETagService etagService;

    public HttpController(UserService userService, ETagService etagService) {
        this.userService = userService;
        this.etagService = etagService;
    }

    public HttpResponse getResponse(HttpRequest httpRequest) {
        if (httpRequest.isGetRequest()) {
            return doGet(httpRequest);
        }
        if (httpRequest.isPostRequest()) {
            return doPost(httpRequest);
        }
        return doOtherMethod(httpRequest);
    }

    private HttpResponse doGet(HttpRequest httpRequest) {
        URL resource = getURL(httpRequest.getPath());
        String eTag = getETag(resource);
        byte[] body = getBodyFromURL(resource);
        if (hasETagAndIsMatch(httpRequest, eTag)) {
            return HttpResponse.notModified(httpRequest.getHttpVersion(), body, httpRequest.getETag());
        }
        return HttpResponse.ok(httpRequest.getPath(), httpRequest.getHttpVersion(), body, eTag);
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

    private HttpResponse doPost(HttpRequest httpRequest) {
        if (httpRequest.isLoginRequest()) {
            return doLogin(httpRequest);
        }
        if (httpRequest.isRegisterRequest()) {
            return doRegister(httpRequest);
        }
        throw new InvalidUrlException("This url does not exist. :" + httpRequest.getPath());
    }

    private HttpResponse doLogin(HttpRequest httpRequest) {
        try {
            userService
                .login(httpRequest.getBodyParam("account"), httpRequest.getBodyParam("password"));
            return HttpResponse.redirect("/index.html", httpRequest.getHttpVersion(), 302);
        } catch (InvalidPasswordException e) {
            logger.info(e.getMessage());
            return HttpResponse.redirect("/401.html", httpRequest.getHttpVersion(), 401);
        }
    }

    private HttpResponse doRegister(HttpRequest httpRequest) {
        userService.register(httpRequest.getBodyParam("account"),
            httpRequest.getBodyParam("password"),
            httpRequest.getBodyParam("email"));
        return HttpResponse.redirect("/index.html", httpRequest.getHttpVersion(), 302);
    }

    private HttpResponse doOtherMethod(HttpRequest httpRequest) {
        return HttpResponse.notFound(httpRequest.getHttpVersion());
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
