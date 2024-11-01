package lotto.config.io;

import lotto.config.io.writer.WriterConfig;
import lotto.io.OutputHandler;

public class OutputHandlerConfig {

    private final OutputHandler outputHandler;

    public OutputHandlerConfig(WriterConfig writerConfig) {
        this.outputHandler = new OutputHandler(writerConfig.getWriter());
    }

    public OutputHandler getOutputHandler() {
        return outputHandler;
    }
}
