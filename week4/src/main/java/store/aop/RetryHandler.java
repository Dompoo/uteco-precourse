package store.aop;


import store.exception.ExceptionHandler;

public class RetryHandler {

    private static final int MAX_RETRY = 10;

    private final ExceptionHandler exceptionHandler;

    public RetryHandler(ExceptionHandler exceptionHandler) {
        this.exceptionHandler = exceptionHandler;
    }

    public <T> T tryUntilSuccess(final IllegalArgumentExceptionThrower<T> thrower) {
        int attemps = 1;
        while (attemps++ <= MAX_RETRY) {
            try {
                return thrower.run();
            } catch (IllegalArgumentException illegalArgumentException) {
                exceptionHandler.handleException(illegalArgumentException);
            }
        }
        throw new RuntimeException("최대 시도 회수를 초과하였습니다.");
    }

    @FunctionalInterface
    public interface IllegalArgumentExceptionThrower<T> {
        T run() throws IllegalArgumentException;
    }
}
