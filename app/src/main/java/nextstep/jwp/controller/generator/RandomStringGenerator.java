package nextstep.jwp.controller.generator;

import java.util.UUID;

public class RandomStringGenerator implements StringGenerator {

    @Override
    public String createRandom() {
        return UUID.randomUUID().toString();
    }
}
