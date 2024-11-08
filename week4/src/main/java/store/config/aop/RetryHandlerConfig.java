package store.config.aop;

import store.aop.RetryHandler;
import store.config.io.writer.WriterConfig;
import store.exception.ExceptionHandler;

public class RetryHandlerConfig {

    private final RetryHandler retryHandler;

    public RetryHandlerConfig(WriterConfig writerConfig) {
        this.retryHandler = new RetryHandler(new ExceptionHandler(writerConfig.getWriter()));
    }

    public RetryHandler getRetryHandler() {
        return this.retryHandler;
    }
}
