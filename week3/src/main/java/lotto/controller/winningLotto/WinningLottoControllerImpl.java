package lotto.controller.winningLotto;

import java.util.List;
import lotto.domain.Lotto;
import lotto.domain.WinningLotto;
import lotto.io.inputHandler.InputHandler;

public class WinningLottoControllerImpl implements WinningLottoController {

    private final InputHandler inputHandler;

    public WinningLottoControllerImpl(InputHandler inputHandler) {
        this.inputHandler = inputHandler;
    }

    @Override
    public Lotto getWinningNumbers() {
        List<Integer> winningNumbers = inputHandler.handleWinningNumbers();
        return Lotto.from(winningNumbers);
    }

    @Override
    public WinningLotto getWinningLotto(Lotto lotto) {
        int bonusNumber = inputHandler.handleBonusNumber();
        return WinningLotto.of(lotto, bonusNumber);
    }
}
