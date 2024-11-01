package lotto.controller;

import lotto.domain.Money;
import lotto.domain.WinningLotto;

public interface LottoController {

    Money getMoney();

    WinningLotto getWinningLotto();
}
