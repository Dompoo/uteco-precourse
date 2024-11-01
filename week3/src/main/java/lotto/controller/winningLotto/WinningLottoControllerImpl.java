package lotto.controller.winningLotto;

import java.util.List;
import lotto.aop.RetryHandler;
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
    public WinningLotto getWinningLotto() {
        return retryHandler.tryUntilSuccess(() -> {
            List<Integer> winningNumbers = inputHandler.handleWinningNumbers();
            int bonusNumber = inputHandler.handleBonusNumber();

            return WinningLotto.of(winningNumbers, bonusNumber);
        });
    }
}
