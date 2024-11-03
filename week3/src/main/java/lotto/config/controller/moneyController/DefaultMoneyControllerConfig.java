package lotto.config.controller.moneyController;

import lotto.config.RetryHandlerConfig;
import lotto.config.io.InputHandlerConfig;
import lotto.controller.money.DefaultMoneyController;
import lotto.controller.money.MoneyController;
import lotto.controller.money.MoneyControllerRetryProxy;

public class DefaultMoneyControllerConfig implements MoneyControllerConfig {

    private final MoneyController moneyController;

    public DefaultMoneyControllerConfig(
            InputHandlerConfig inputHandlerConfig,
            RetryHandlerConfig retryHandlerConfig
    ) {
        DefaultMoneyController defaultMoneyController = new DefaultMoneyController(
                inputHandlerConfig.getInputHandler()
        );
        this.moneyController = new MoneyControllerRetryProxy(
                defaultMoneyController,
                retryHandlerConfig.getRetryHandler()
        );
    }

    @Override
    public MoneyController getMoneyController() {
        return this.moneyController;
    }
}
