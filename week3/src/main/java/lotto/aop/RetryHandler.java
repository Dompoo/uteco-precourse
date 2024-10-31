package lotto.aop;

import lotto.exception.OverMaxRetryAttempt;
import lotto.io.writer.Writer;

public class RetryHandler {

    private static final int MAX_RETRY = 10;

    private final Writer writer;

    public RetryHandler(Writer writer) {
        this.writer = writer;
    }

    public <T> T tryUntilSuccess(ExceptionThrower<T> thrower) {
        int attemps = 1;
        while (attemps++ <= MAX_RETRY) {
            try {
                return thrower.run();
            } catch (Exception e) {
                writer.writeLine(e.getMessage());
            }
        }
        throw new OverMaxRetryAttempt();
    }

    @FunctionalInterface
    public interface ExceptionThrower<T> {
        T run() throws Exception;
    }
}
