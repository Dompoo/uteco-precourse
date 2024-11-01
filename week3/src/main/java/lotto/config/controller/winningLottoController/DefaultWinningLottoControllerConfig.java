package lotto.config.controller.winningLottoController;

import lotto.config.RetryHandlerConfig;
import lotto.config.io.InputHandlerConfig;
import lotto.controller.winningLotto.WinningLottoController;
import lotto.controller.winningLotto.WinningLottoControllerImpl;
import lotto.controller.winningLotto.WinningLottoControllerRetryProxy;

public class DefaultWinningLottoControllerConfig implements WinningLottoControllerConfig {

    private final WinningLottoController winningLottoController;

    public DefaultWinningLottoControllerConfig(
            InputHandlerConfig inputHandlerConfig,
            RetryHandlerConfig retryHandlerConfig
    ) {
        WinningLottoControllerImpl winningLottoController = new WinningLottoControllerImpl(
                inputHandlerConfig.getInputHandler()
        );
        this.winningLottoController = new WinningLottoControllerRetryProxy(
                winningLottoController,
                retryHandlerConfig.getRetryHandler()
        );
    }

    @Override
    public WinningLottoController getWinningLottoController() {
        return this.winningLottoController;
    }
}
