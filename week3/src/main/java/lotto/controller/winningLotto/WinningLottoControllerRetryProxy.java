package lotto.controller.winningLotto;

import lotto.aop.RetryHandler;
import lotto.domain.Lotto;
import lotto.domain.WinningLotto;

public class WinningLottoControllerRetryProxy implements WinningLottoController {

    private final WinningLottoController target;
    private final RetryHandler retryHandler;

    public WinningLottoControllerRetryProxy(WinningLottoController target, RetryHandler retryHandler) {
        this.target = target;
        this.retryHandler = retryHandler;
    }

    @Override
    public Lotto getWinningNumbers() {
        return retryHandler.tryUntilSuccess(target::getWinningNumbers);
    }

    @Override
    public WinningLotto getWinningLotto(Lotto lotto) {
        return retryHandler.tryUntilSuccess(() -> target.getWinningLotto(lotto));
    }
}
