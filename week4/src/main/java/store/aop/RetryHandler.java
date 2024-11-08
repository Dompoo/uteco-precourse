package store.aop;


import store.exception.ExceptionHandler;

public class RetryHandler {

    private final ExceptionHandler exceptionHandler;

    public RetryHandler(ExceptionHandler exceptionHandler) {
        this.exceptionHandler = exceptionHandler;
    }

    public <T> T tryUntilSuccess(final IllegalArgumentExceptionThrower<T> thrower) {
        while (true) {
            try {
                return thrower.run();
            } catch (IllegalArgumentException illegalArgumentException) {
                exceptionHandler.handleException(illegalArgumentException);
            }
        }
    }

    @FunctionalInterface
    public interface IllegalArgumentExceptionThrower<T> {
        T run() throws IllegalArgumentException;
    }
}
