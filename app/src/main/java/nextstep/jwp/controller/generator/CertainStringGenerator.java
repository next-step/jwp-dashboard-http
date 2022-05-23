package nextstep.jwp.controller.generator;

public class CertainStringGenerator implements StringGenerator {

    @Override
    public String createRandom() {
        return "J_SESSION_ID";
    }
}
