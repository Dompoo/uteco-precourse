package lotto.controller;

import java.util.List;
import lotto.controller.lotto.LottoController;
import lotto.controller.lottoStatics.LottoStaticsController;
import lotto.controller.money.MoneyController;
import lotto.controller.winningLotto.WinningLottoController;
import lotto.domain.Lotto;
import lotto.domain.Money;
import lotto.domain.WinningLotto;

public class LottoApplication {

    private final MoneyController moneyController;
    private final LottoController lottoController;
    private final WinningLottoController winningLottoController;
    private final LottoStaticsController lottoStaticsController;

    public LottoApplication(
            MoneyController moneyController,
            LottoController lottoController,
            WinningLottoController winningLottoController,
            LottoStaticsController lottoStaticsController
    ) {
        this.moneyController = moneyController;
        this.lottoController = lottoController;
        this.winningLottoController = winningLottoController;
        this.lottoStaticsController = lottoStaticsController;
    }

    public void run() {
        Money money = moneyController.getMoney();
        List<Lotto> purchasedLottos = lottoController.purchaseLottos(money);
        Lotto lotto = winningLottoController.getWinningNumbers();
        WinningLotto winningLotto = winningLottoController.getWinningLotto(lotto);
        lottoStaticsController.calculateStatics(purchasedLottos, winningLotto, money);
    }
}
