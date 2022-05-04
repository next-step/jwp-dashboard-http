package nextstep.jwp.service;

import nextstep.jwp.db.InMemoryUserRepository;
import nextstep.jwp.exception.DuplicatedAccountException;
import nextstep.jwp.exception.InvalidPasswordException;
import nextstep.jwp.exception.UserNotFoundException;
import nextstep.jwp.model.User;

public class UserService {


    public void login(String account, String password) {
        User user = InMemoryUserRepository.findByAccount(account)
            .orElseThrow(() -> new UserNotFoundException("User does not exist with account : " + account));
        checkPassword(user, password);
    }

    private void checkPassword(User user, String password) {
        if (!user.checkPassword(password)) {
            throw new InvalidPasswordException("Password is invalid");
        }
    }

    public void register(String account, String password, String email) {
        checkDuplicated(account);
        InMemoryUserRepository.save(new User(2, account, password, email));
    }

    private void checkDuplicated(String account) {
        if (InMemoryUserRepository.existsByAccount(account)) {
            throw new DuplicatedAccountException("This Account already exists. : " + account);
        }
    }


}
