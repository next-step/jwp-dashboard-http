package nextstep.jwp.model.http.httpsession;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import nextstep.jwp.controller.generator.CertainStringGenerator;
import nextstep.jwp.exception.SessionAttributeInvalidException;
import nextstep.jwp.model.User;
import nextstep.jwp.model.UserTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class HttpSessionTest {


    private static final String EMPTY_NAME_EXCEPTION = "Session attribute name could not be empty or blank.";
    private static final String ALREADY_EXIST_NAME_EXCEPTION = "Session already have attribute named ";
    private static final String EMPTY_OBJECT_EXCEPTION = "Session attribute object could not be null.";

    private HttpSession httpSession;

    @BeforeEach
    void setUp() {
        httpSession = HttpSession.of(new CertainStringGenerator());
        httpSession.addAttribute("user", UserTest.USER);
    }

    @Test
    public void add_attribute() {
        assertThat(httpSession.getSessionObjects().size()).isEqualTo(1);
        assertThat(httpSession.getSessionObjects().get(0).getObject()).isEqualTo(UserTest.USER);
    }

    @Test
    public void add_attribute_with_empty_name_is_invalid() {
        assertThatThrownBy(() -> httpSession.addAttribute("", UserTest.USER))
            .isInstanceOf(SessionAttributeInvalidException.class)
            .hasMessage(EMPTY_NAME_EXCEPTION);
    }

    @Test
    public void add_attribute_with_already_existing_name_is_invalid() {
        assertThatThrownBy(() -> httpSession.addAttribute("user", UserTest.USER))
            .isInstanceOf(SessionAttributeInvalidException.class)
            .hasMessageContaining(ALREADY_EXIST_NAME_EXCEPTION);
    }

    @Test
    public void add_attribute_with_empty_object_is_invalid() {
        assertThatThrownBy(() -> httpSession.addAttribute("test", null))
            .isInstanceOf(SessionAttributeInvalidException.class)
            .hasMessage(EMPTY_OBJECT_EXCEPTION);
    }

    @Test
    public void remove_attribute() {
        //given
        String authority = "authority";
        httpSession.addAttribute(authority, "member");

        // when
        httpSession.removeAttribute(authority);

        //then
        assertThat(httpSession.getSessionObjects().size()).isEqualTo(1);
        assertThat(httpSession.getSessionObjects().get(0).getObject()).isEqualTo(UserTest.USER);
    }

    @Test
    public void get_attribute() {
        // when
        User user = (User) httpSession.getAttribute("user");

        // then
        assertThat(user).isEqualTo(UserTest.USER);
    }


}