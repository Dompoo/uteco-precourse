package lotto.controller.winningLotto;

import lotto.domain.Lotto;
import lotto.domain.WinningLotto;

public interface WinningLottoController {

    Lotto readWinningNumbers();

    WinningLotto createWinningLotto(Lotto lotto);
}
