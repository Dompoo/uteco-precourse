package lotto;

import java.util.List;
import lotto.config.LottoConfig;
import lotto.domain.Lotto;
import lotto.domain.LottoStatics;
import lotto.domain.Money;
import lotto.domain.WinningLotto;
import lotto.domain.numberProvider.NumberPicker;
import lotto.dto.IncomeStatics;
import lotto.dto.PrizeStatics;
import lotto.dto.PurchasedLottoDto;
import lotto.io.InputHandler;
import lotto.io.OutputHandler;

public class LottoController {

    private final InputHandler inputHandler;
    private final OutputHandler outputHandler;
    private final RetryHandler retryHandler;
    private final NumberPicker numberPicker;

    public LottoController(
            LottoConfig lottoConfig
    ) {
        this.inputHandler = lottoConfig.getInputHandler();
        this.outputHandler = lottoConfig.getOutputHandler();
        this.retryHandler = lottoConfig.getRetryHandler();
        this.numberPicker = lottoConfig.getNumberPicker();
    }

    public void run() {
        Money money = retryHandler.tryUntilSuccess(() -> {
            int amount = inputHandler.handlePurchaseAmount();
            return Money.from(amount);
        });

        List<Lotto> purchasedLottos = Lotto.purchase(money, numberPicker);

        outputHandler.handlePurchasedLottos(PurchasedLottoDto.from(purchasedLottos));

        Lotto lotto = retryHandler.tryUntilSuccess(() -> {
            List<Integer> numbers = inputHandler.handleWinningLottoNumbers();
            return Lotto.from(numbers);
        });

        WinningLotto winningLotto = retryHandler.tryUntilSuccess(() -> {
            int bonusNumber = inputHandler.handleWinningLottoBonusNumber();
            return WinningLotto.of(lotto, bonusNumber);
        });

        LottoStatics lottoStatics = LottoStatics.of(purchasedLottos, winningLotto, money);

        outputHandler.handlePrizeStatics(PrizeStatics.from(lottoStatics));
        outputHandler.handleIncomeStatics(IncomeStatics.from(lottoStatics));
    }
}
