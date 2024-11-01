package lotto.controller.lotto;

import java.util.List;
import lotto.domain.Lotto;
import lotto.domain.Money;
import lotto.domain.numberPicker.NumberPicker;
import lotto.dto.PurchasedLottoDto;
import lotto.io.OutputHandler;

public class LottoControllerImpl implements LottoController {

    private final OutputHandler outputHandler;
    private final NumberPicker numberPicker;

    public LottoControllerImpl(OutputHandler outputHandler, NumberPicker numberPicker) {
        this.outputHandler = outputHandler;
        this.numberPicker = numberPicker;
    }

    @Override
    public List<Lotto> purchaseLottos(Money money) {
        List<Lotto> purchasedLottos = Lotto.purchase(money, numberPicker);
        outputHandler.handlePurchasedLottos(PurchasedLottoDto.from(purchasedLottos));
        return purchasedLottos;
    }
}
