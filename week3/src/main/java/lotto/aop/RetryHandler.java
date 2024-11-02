package lotto.aop;

import lotto.exception.OverMaxRetryException;
import lotto.io.OutputHandler;

public class RetryHandler {

    private static final int MAX_RETRY = 10;

    private final OutputHandler outputHandler;

    public RetryHandler(OutputHandler outputHandler) {
        this.outputHandler = outputHandler;
    }

    public <T> T tryUntilSuccess(IllegalArgumentExceptionThrower<T> thrower) {
        int attemps = 1;
        while (attemps++ <= MAX_RETRY) {
            try {
                return thrower.run();
            } catch (IllegalArgumentException illegalArgumentException) {
                outputHandler.handleException(illegalArgumentException);
            } catch (Exception exception) {
                outputHandler.handleException(exception);
                throw exception;
            }
        }
        OverMaxRetryException overMaxRetryException = new OverMaxRetryException();
        outputHandler.handleException(overMaxRetryException);
        throw overMaxRetryException;
    }

    @FunctionalInterface
    public interface IllegalArgumentExceptionThrower<T> {
        T run() throws IllegalArgumentException;
    }
}
