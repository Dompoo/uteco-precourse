package lotto.config;

import lotto.RetryHandler;
import lotto.config.numberPricker.NumberPickerConfig;
import lotto.config.reader.ReaderConfig;
import lotto.config.writer.WriterConfig;
import lotto.domain.numberProvider.NumberPicker;
import lotto.io.InputHandler;
import lotto.io.OutputHandler;

public class LottoConfig {

    private final InputHandler inputHandler;
    private final OutputHandler outputHandler;
    private final RetryHandler retryHandler;
    private final NumberPicker numberPicker;

    public LottoConfig(
            ReaderConfig readerConfig,
            WriterConfig writerConfig,
            NumberPickerConfig numberPickerConfig
    ) {
        this.inputHandler = new InputHandler(readerConfig.getReader(), writerConfig.getWriter());
        this.outputHandler = new OutputHandler(writerConfig.getWriter());
        this.retryHandler = new RetryHandler(writerConfig.getWriter());
        this.numberPicker = numberPickerConfig.getNumberPicker();
    }

    public InputHandler getInputHandler() {
        return this.inputHandler;
    }

    public OutputHandler getOutputHandler() {
        return this.outputHandler;
    }

    public RetryHandler getRetryHandler() {
        return this.retryHandler;
    }

    public NumberPicker getNumberPicker() {
        return this.numberPicker;
    }
}
