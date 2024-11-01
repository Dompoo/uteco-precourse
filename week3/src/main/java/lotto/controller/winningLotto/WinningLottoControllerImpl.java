package lotto.controller.winningLotto;

import java.util.List;
import lotto.aop.RetryHandler;
import lotto.domain.Lotto;
import lotto.domain.WinningLotto;
import lotto.io.InputHandler;

public class WinningLottoControllerImpl implements WinningLottoController {

    private final InputHandler inputHandler;
    private final RetryHandler retryHandler;

    public WinningLottoControllerImpl(InputHandler inputHandler, RetryHandler retryHandler) {
        this.inputHandler = inputHandler;
        this.retryHandler = retryHandler;
    }

    @Override
    public Lotto getWinningNumbers() {
        return retryHandler.tryUntilSuccess(() -> {
            List<Integer> winningNumbers = inputHandler.handleWinningNumbers();
            return Lotto.from(winningNumbers);
        });
    }

    @Override
    public WinningLotto getWinningLotto(Lotto lotto) {
        return retryHandler.tryUntilSuccess(() -> {
            int bonusNumber = inputHandler.handleBonusNumber();
            return WinningLotto.of(lotto, bonusNumber);
        });
    }
}
