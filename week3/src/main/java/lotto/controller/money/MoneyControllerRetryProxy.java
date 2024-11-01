package lotto.controller.money;

import lotto.aop.RetryHandler;
import lotto.domain.Money;

public class MoneyControllerRetryProxy implements MoneyController {

    private final MoneyController target;
    private final RetryHandler retryHandler;

    public MoneyControllerRetryProxy(MoneyController target, RetryHandler retryHandler) {
        this.target = target;
        this.retryHandler = retryHandler;
    }

    @Override
    public Money getMoney() {
        return retryHandler.tryUntilSuccess(target::getMoney);
    }
}
