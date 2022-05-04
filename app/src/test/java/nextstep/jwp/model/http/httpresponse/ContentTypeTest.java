package nextstep.jwp.model.http.httpresponse;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import nextstep.jwp.exception.ContentTypeNotFoundException;
import nextstep.jwp.model.http.httpresponse.ContentType;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ContentTypeTest {

    private final String CONTENT_TYPE_NOT_FOUND_EXCEPTION = "ContentType does not exist : ";

    @Test
    public void find_content_type_with_type() {
        String path = "/index.html";
        String contentType = ContentType.contentType(path);
        assertThat(contentType).isEqualTo("text/html;charset=utf-8");
    }

    @Test
    public void find_content_type_with_not_exist_type_is_invalid() {
        String path = "/index.abc";
        assertThatThrownBy(() -> ContentType.contentType(path))
            .isInstanceOf(ContentTypeNotFoundException.class)
            .hasMessageContaining(CONTENT_TYPE_NOT_FOUND_EXCEPTION);
    }


}