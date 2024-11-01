package lotto.controller.money;

import lotto.domain.Money;
import lotto.io.InputHandler;

public class MoneyControllerImpl implements MoneyController {

    private final InputHandler inputHandler;

    public MoneyControllerImpl(InputHandler inputHandler) {
        this.inputHandler = inputHandler;
    }

    @Override
    public Money getMoney() {
        int amount = inputHandler.handlePurchaseCost();
        return Money.from(amount);
    }
}
