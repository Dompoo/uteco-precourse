package lotto.controller.lottoStatics;

import java.util.List;
import lotto.domain.Lotto;
import lotto.domain.LottoStatics;
import lotto.domain.Money;
import lotto.domain.WinningLotto;
import lotto.dto.IncomeStatics;
import lotto.dto.PrizeStatics;
import lotto.io.OutputHandler;

public class DefaultLottoStaticsController implements LottoStaticsController {

    private final OutputHandler outputHandler;

    public DefaultLottoStaticsController(OutputHandler outputHandler) {
        this.outputHandler = outputHandler;
    }

    @Override
    public void printLottoStatics(List<Lotto> purchasedLottos, WinningLotto winningLotto, Money money) {
        LottoStatics lottoStatics = LottoStatics.of(purchasedLottos, winningLotto, money);
        outputHandler.handlePrizeStatics(PrizeStatics.from(lottoStatics));
        outputHandler.handleIncomeStatics(IncomeStatics.from(lottoStatics));
    }
}