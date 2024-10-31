package lotto.domain;

import java.util.List;
import java.util.Optional;
import lotto.exception.BonusNumberDuplicatedException;
import lotto.exception.BonusNumberNullException;
import lotto.exception.LottoNullException;

public class WinningLotto {

    private final Lotto lotto;
    private final Number bonusNumber;

    private WinningLotto(Lotto lotto, Number bonusNumber) {
        validate(lotto, bonusNumber);
        this.lotto = lotto;
        this.bonusNumber = bonusNumber;
    }

    private static void validate(Lotto lotto, Number bonusNumber) {
        if (lotto == null) {
            throw new LottoNullException();
        }

        if (bonusNumber == null) {
            throw new BonusNumberNullException();
        }

        if (lotto.contains(bonusNumber)) {
            throw new BonusNumberDuplicatedException();
        }
    }

    public static WinningLotto of(List<Integer> numbers, int bonusNumber) {
        return new WinningLotto(Lotto.from(numbers), Number.from(bonusNumber));
    }

    public Optional<LottoPrize> match(Lotto targetLotto) {
        int numberMatch = targetLotto.getMatchCount(lotto);
        boolean bonusNumberMatch = targetLotto.contains(bonusNumber);

        return LottoPrize.calculatePrize(numberMatch, bonusNumberMatch);
    }
}
