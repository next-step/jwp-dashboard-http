package nextstep.jwp.model.http;

import static org.junit.jupiter.api.Assertions.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.security.InvalidParameterException;
import nextstep.jwp.exception.ParamNotFoundException;
import org.junit.jupiter.api.Test;

class ParamsTest {

    private final String VALID_QUERY = "account=account&password=password";
    private final String INVALID_QUERY = "account?";
    private final String INVALID_PARAMETER_EXCEPTION = "This parameter is not invalid.";
    private final String PARAM_NOT_FOUND_EXCEPTION = "Parameter does not exist.";

    @Test
    public void create_params_with_query() {
        Params params = Params.of(VALID_QUERY);
        assertThat(params.getParams().size()).isEqualTo(2);
        assertThat(params.getParam("account")).isEqualTo("account");
        assertThat(params.getParam("password")).isEqualTo("password");
    }

    @Test
    public void create_params_with_invalid_query() {
        assertThatThrownBy(() -> Params.of(INVALID_QUERY))
            .isInstanceOf(InvalidParameterException.class)
            .hasMessage(INVALID_PARAMETER_EXCEPTION);
    }

    @Test
    public void find_param_with_name() {
        Params params = Params.of(VALID_QUERY);
        String value = params.getParam("account");
        String expected = "account";
        assertThat(value).isEqualTo(expected);
    }

    @Test
    public void find_param_with_invalid_name() {
        Params params = Params.of(VALID_QUERY);
        assertThatThrownBy(() -> params.getParam("test"))
            .isInstanceOf(ParamNotFoundException.class)
            .hasMessage(PARAM_NOT_FOUND_EXCEPTION);
    }

}