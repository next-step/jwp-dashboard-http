package nextstep.jwp.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import nextstep.jwp.controller.generator.CertainStringGenerator;
import nextstep.jwp.db.InMemoryUserRepository;
import nextstep.jwp.exception.DuplicatedAccountException;
import nextstep.jwp.exception.InvalidPasswordException;
import nextstep.jwp.exception.UserNotFoundException;
import org.junit.jupiter.api.Test;

class UserServiceTest {

    private final String account = "gugu";
    private final String password = "password";
    private final String email = "test@example.com";
    private final String USER_NOT_FOUND_EXCEPTION = "User does not exist with account : ";
    private final String INVALID_PASSWORD_EXCEPTION = "Password is invalid";
    private final String DUPLICATED_ACCOUNT_EXCEPTION = "This Account already exists. : ";

    private UserService userService = new UserService(new CertainStringGenerator());

    @Test
    public void login_with_not_registered_account() {
        assertThatThrownBy(() -> userService.login("account", password))
            .isInstanceOf(UserNotFoundException.class)
            .hasMessageContaining(USER_NOT_FOUND_EXCEPTION);
    }

    @Test
    public void login_with_invalid_password() {
        assertThatThrownBy(() -> userService.login(account, "pass"))
            .isInstanceOf(InvalidPasswordException.class)
            .hasMessage(INVALID_PASSWORD_EXCEPTION);
    }

    @Test
    public void register_with_valid_account() {
        String account = "gugugu";
        userService.register(account, password, email);
        assertThat(InMemoryUserRepository.existsByAccount(account)).isTrue();
    }

    @Test
    public void register_with_already_exist_account() {
        assertThatThrownBy(() -> userService.register(account, "pass", email))
            .isInstanceOf(DuplicatedAccountException.class)
            .hasMessageContaining(DUPLICATED_ACCOUNT_EXCEPTION);
    }

}