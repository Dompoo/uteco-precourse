package lotto.controller.money;

import lotto.aop.RetryHandler;
import lotto.domain.Money;
import lotto.io.InputHandler;

public class MoneyControllerImpl implements MoneyController {

    private final InputHandler inputHandler;
    private final RetryHandler retryHandler;

    public MoneyControllerImpl(InputHandler inputHandler, RetryHandler retryHandler) {
        this.inputHandler = inputHandler;
        this.retryHandler = retryHandler;
    }

    @Override
    public Money getMoney() {
        return retryHandler.tryUntilSuccess(() -> {
            int amount = inputHandler.handlePurchaseCost();
            return Money.from(amount);
        });
    }
}
