package lotto;

import lotto.io.writer.Writer;

public class RetryHandler {

    private final Writer writer;

    public RetryHandler(Writer writer) {
        this.writer = writer;
    }

    public <T> T tryUntilSuccess(ExceptionThrower<T> thrower) {
        while (true) {
            try {
                return thrower.run();
            } catch (Exception e) {
                writer.writeLine(e.getMessage());
            }
        }
    }

    @FunctionalInterface
    public interface ExceptionThrower<T> {
        T run() throws Exception;
    }
}
