package lotto.domain;

import java.util.Optional;
import lotto.domain.validator.ParamsValidator;
import lotto.exception.BonusNumberDuplicatedException;

public class WinningLotto {

    private final Lotto lotto;
    private final Number bonusNumber;

    private WinningLotto(Lotto lotto, Number bonusNumber) {
        validateBonusNumberNotDuplicated(lotto, bonusNumber);
        this.lotto = lotto;
        this.bonusNumber = bonusNumber;
    }

    private static void validateBonusNumberNotDuplicated(Lotto lotto, Number bonusNumber) {
        if (lotto.contains(bonusNumber)) {
            throw new BonusNumberDuplicatedException();
        }
    }

    public static WinningLotto of(Lotto lotto, Integer bonusNumber) {
        ParamsValidator.validateParamsNotNull(WinningLotto.class, lotto, bonusNumber);

        return new WinningLotto(lotto, Number.from(bonusNumber));
    }

    public Optional<LottoPrize> match(Lotto targetLotto) {
        ParamsValidator.validateParamsNotNull(WinningLotto.class, targetLotto);

        int numberMatch = targetLotto.getMatchCount(lotto);
        boolean bonusNumberMatch = targetLotto.contains(bonusNumber);

        return LottoPrize.calculatePrize(numberMatch, bonusNumberMatch);
    }
}
