package store.exception;

import store.io.writer.Writer;

public class ExceptionHandler {

    private static final String EXCEPTION_MESSAGE_PREFIX = "[ERROR] ";

    private final Writer writer;

    public ExceptionHandler(Writer writer) {
        this.writer = writer;
    }

    public void handleException(Exception exception) {
        writer.writeLine(EXCEPTION_MESSAGE_PREFIX + exception.getMessage());
    }
}
