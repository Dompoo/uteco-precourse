package lotto.controller.lottoStatics;

import java.util.List;
import lotto.domain.Lotto;
import lotto.domain.Money;
import lotto.domain.WinningLotto;

public interface LottoStaticsController {

    void calculateStatics(List<Lotto> purchasedLottos, WinningLotto winningLotto, Money money);
}
