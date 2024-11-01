package lotto.config;

import lotto.config.controller.lottoController.DefaultLottoControllerConfig;
import lotto.config.controller.lottoController.LottoControllerConfig;
import lotto.config.controller.lottoStaticsController.DefaultLottoStaticsControllerConfig;
import lotto.config.controller.lottoStaticsController.LottoStaticsControllerConfig;
import lotto.config.controller.moneyController.DefaultMoneyControllerConfig;
import lotto.config.controller.moneyController.MoneyControllerConfig;
import lotto.config.controller.winningLottoController.DefaultWinningLottoControllerConfig;
import lotto.config.controller.winningLottoController.WinningLottoControllerConfig;
import lotto.config.io.InputHandlerConfig;
import lotto.config.io.OutputHandlerConfig;
import lotto.config.io.reader.DefaultReaderConfig;
import lotto.config.io.reader.ReaderConfig;
import lotto.config.io.writer.DefaultWriterConfig;
import lotto.config.io.writer.WriterConfig;
import lotto.config.numberPricker.DefaultNumberPickerConfig;
import lotto.controller.LottoApplication;

public class ApplicationConfig {

    private final LottoApplication lottoApplication;

    public ApplicationConfig() {
        ReaderConfig readerConfig = new DefaultReaderConfig();
        WriterConfig writerConfig = new DefaultWriterConfig();

        InputHandlerConfig inputHandlerConfig = new InputHandlerConfig(readerConfig, writerConfig);
        OutputHandlerConfig outputHandlerConfig = new OutputHandlerConfig(writerConfig);
        RetryHandlerConfig retryHandlerConfig = new RetryHandlerConfig(outputHandlerConfig);

        LottoControllerConfig lottoControllerConfig = new DefaultLottoControllerConfig(
                outputHandlerConfig,
                new DefaultNumberPickerConfig()
        );
        LottoStaticsControllerConfig lottoStaticsControllerConfig = new DefaultLottoStaticsControllerConfig(
                outputHandlerConfig
        );
        MoneyControllerConfig moneyControllerConfig = new DefaultMoneyControllerConfig(
                inputHandlerConfig,
                retryHandlerConfig
        );
        WinningLottoControllerConfig winningLottoControllerConfig = new DefaultWinningLottoControllerConfig(
                inputHandlerConfig,
                retryHandlerConfig
        );
        this.lottoApplication = new LottoApplication(
                moneyControllerConfig.getMoneyController(),
                lottoControllerConfig.getLottoController(),
                winningLottoControllerConfig.getWinningLottoController(),
                lottoStaticsControllerConfig.getLottoStaticsController()
        );
    }

    public LottoApplication getLottoApplication() {
        return this.lottoApplication;
    }
}
