package lotto.controller;

import java.util.List;
import lotto.config.LottoConfig;
import lotto.controller.lotto.LottoController;
import lotto.controller.lotto.LottoControllerImpl;
import lotto.controller.lottoStatics.LottoStaticsController;
import lotto.controller.lottoStatics.LottoStaticsControllerImpl;
import lotto.controller.money.MoneyController;
import lotto.controller.money.MoneyControllerImpl;
import lotto.controller.winningLotto.WinningLottoController;
import lotto.controller.winningLotto.WinningLottoControllerImpl;
import lotto.domain.Lotto;
import lotto.domain.Money;
import lotto.domain.WinningLotto;

public class ControllerFacade {

    private final MoneyController moneyController;
    private final LottoController lottoController;
    private final WinningLottoController winningLottoController;
    private final LottoStaticsController lottoStaticsController;

    public ControllerFacade(LottoConfig lottoConfig) {
        this.moneyController = new MoneyControllerImpl(lottoConfig.getInputHandler(), lottoConfig.getRetryHandler());
        this.lottoController = new LottoControllerImpl(lottoConfig.getOutputHandler(), lottoConfig.getNumberPicker());
        this.winningLottoController = new WinningLottoControllerImpl(lottoConfig.getInputHandler(),
                lottoConfig.getRetryHandler());
        this.lottoStaticsController = new LottoStaticsControllerImpl(lottoConfig.getOutputHandler());
    }

    public void run() {
        Money money = moneyController.getMoney();
        List<Lotto> purchasedLottos = lottoController.purchaseLottos(money);
        Lotto lotto = winningLottoController.getWinningNumbers();
        WinningLotto winningLotto = winningLottoController.getWinningLotto(lotto);
        lottoStaticsController.calculateStatics(purchasedLottos, winningLotto, money);
    }
}
