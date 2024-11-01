package lotto.controller;

import lotto.aop.RetryHandler;
import lotto.domain.Money;
import lotto.domain.WinningLotto;

public class RetryFilter implements LottoController {

    private final LottoController target;
    private final RetryHandler retryHandler;

    public RetryFilter(LottoController target, RetryHandler retryHandler) {
        this.target = target;
        this.retryHandler = retryHandler;
    }

    @Override
    public Money getMoney() {
        return retryHandler.tryUntilSuccess(target::getMoney);
    }

    @Override
    public WinningLotto getWinningLotto() {
        return retryHandler.tryUntilSuccess(target::getWinningLotto);
    }
}
