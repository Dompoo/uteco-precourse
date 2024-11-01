package lotto.aop;

import lotto.exception.OverMaxRetryAttempt;
import lotto.io.OutputHandler;

public class RetryHandler {

    private static final int MAX_RETRY = 10;

    private final OutputHandler outputHandler;

    public RetryHandler(OutputHandler outputHandler) {
        this.outputHandler = outputHandler;
    }

    public <T> T tryUntilSuccess(ExceptionThrower<T> thrower) {
        int attemps = 1;
        while (attemps++ <= MAX_RETRY) {
            try {
                return thrower.run();
            } catch (Exception e) {
                outputHandler.handleException(e);
            }
        }
        throw new OverMaxRetryAttempt();
    }

    @FunctionalInterface
    public interface ExceptionThrower<T> {
        T run() throws Exception;
    }
}
