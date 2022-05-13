package nextstep.jwp.service;

import nextstep.jwp.db.InMemoryUserRepository;
import nextstep.jwp.exception.DuplicatedAccountException;
import nextstep.jwp.exception.InvalidPasswordException;
import nextstep.jwp.exception.UserNotFoundException;
import nextstep.jwp.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);


    public void login(String account, String password) {
        User user = InMemoryUserRepository.findByAccount(account)
            .orElseThrow(() -> new UserNotFoundException("User does not exist with account : " + account));
        checkPassword(user, password);
        logger.info("Login Success");
    }

    private void checkPassword(User user, String password) {
        if (!user.checkPassword(password)) {
            logger.info("Login Failed");
            throw new InvalidPasswordException("Password is invalid");
        }

    }

    public void register(String account, String password, String email) {
        checkDuplicated(account);
        InMemoryUserRepository.save(new User(2, account, password, email));
        logger.info("User is Registered Successfully.");
    }

    private void checkDuplicated(String account) {
        if (InMemoryUserRepository.existsByAccount(account)) {
            logger.info("Account is already exists.");
            throw new DuplicatedAccountException("This Account already exists. : " + account);
        }
    }


}
