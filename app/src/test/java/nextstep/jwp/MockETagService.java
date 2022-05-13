package nextstep.jwp;

import nextstep.jwp.service.ETagService;

public class MockETagService extends ETagService {

    @Override
    public String getETag(String path) {
        return "abc";
    }
}
