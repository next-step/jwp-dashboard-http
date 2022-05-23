package nextstep.jwp.service;

import java.net.URL;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ETagServiceTest {

    private ETagService etagService = new ETagService();

    @Test
    public void get_eTag() {
        URL resource = Thread.currentThread().getContextClassLoader().getResource("static/index.html");
        String path = resource.getPath();
        String eTag = etagService.getETag(path);
        assertThat(eTag).isNotBlank();
        assertThat(eTag).isNotNull();
    }
}