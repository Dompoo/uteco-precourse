package lotto.controller;

import java.util.List;
import lotto.config.LottoConfig;
import lotto.domain.Lotto;
import lotto.domain.LottoStatics;
import lotto.domain.Money;
import lotto.domain.WinningLotto;
import lotto.domain.numberPicker.NumberPicker;
import lotto.dto.IncomeStatics;
import lotto.dto.PrizeStatics;
import lotto.dto.PurchasedLottoDto;
import lotto.io.InputHandler;
import lotto.io.OutputHandler;

public class LottoControllerImpl implements LottoController {

    private final InputHandler inputHandler;
    private final OutputHandler outputHandler;
    private final NumberPicker numberPicker;

    public LottoControllerImpl(
            LottoConfig lottoConfig
    ) {
        this.inputHandler = lottoConfig.getInputHandler();
        this.outputHandler = lottoConfig.getOutputHandler();
        this.numberPicker = lottoConfig.getNumberPicker();
    }

    public void run() {
        Money money = getMoney();

        List<Lotto> purchasedLottos = Lotto.purchase(money, numberPicker);

        outputHandler.handlePurchasedLottos(PurchasedLottoDto.from(purchasedLottos));

        WinningLotto winningLotto = getWinningLotto();

        LottoStatics lottoStatics = LottoStatics.of(purchasedLottos, winningLotto, money);

        outputHandler.handlePrizeStatics(PrizeStatics.from(lottoStatics));
        outputHandler.handleIncomeStatics(IncomeStatics.from(lottoStatics));
    }

    @Override
    public Money getMoney() {
        int amount = inputHandler.handlePurchaseCost();
        return Money.from(amount);
    }

    @Override
    public WinningLotto getWinningLotto() {
        List<Integer> winningNumbers = inputHandler.handleWinningNumbers();
        int bonusNumber = inputHandler.handleBonusNumber();
        return WinningLotto.of(winningNumbers, bonusNumber);
    }
}
