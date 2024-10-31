package lotto;

import java.util.List;
import lotto.domain.Lotto;
import lotto.domain.LottoStatics;
import lotto.domain.Money;
import lotto.domain.WinningLotto;
import lotto.domain.numberProvider.NumberPicker;
import lotto.io.InputHandler;
import lotto.io.OutputHandler;

public class LottoController {

    private final InputHandler inputHandler;
    private final OutputHandler outputHandler;
    private final RetryHandler retryHandler;
    private final NumberPicker numberPicker;

    public LottoController(
            InputHandler inputHandler,
            OutputHandler outputHandler,
            RetryHandler retryHandler,
            NumberPicker numberPicker
    ) {
        this.inputHandler = inputHandler;
        this.outputHandler = outputHandler;
        this.retryHandler = retryHandler;
        this.numberPicker = numberPicker;
    }

    public void run() {
        Money money = retryHandler.tryUntilSuccess(() -> {
            int amount = inputHandler.handlePurchaseAmount();
            return Money.from(amount);
        });

        List<Lotto> purchasedLottos = Lotto.purchase(money, numberPicker);

        outputHandler.handlePurchasedLottos(purchasedLottos);

        Lotto lotto = retryHandler.tryUntilSuccess(() -> {
            List<Integer> numbers = inputHandler.handleWinningLottoNumbers();
            return Lotto.from(numbers);
        });

        WinningLotto winningLotto = retryHandler.tryUntilSuccess(() -> {
            int bonusNumber = inputHandler.handleWinningLottoBonusNumber();
            return WinningLotto.of(lotto, bonusNumber);
        });

        LottoStatics lottoStatics = LottoStatics.of(purchasedLottos, winningLotto, money);

        outputHandler.handlePrizeStatics(lottoStatics.getPrizeStatics());
        outputHandler.handleIncomeStatics(lottoStatics.getIncomeStatics());
    }
}
