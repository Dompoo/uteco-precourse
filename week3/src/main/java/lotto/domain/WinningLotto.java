package lotto.domain;

import java.util.List;

public class WinningLotto {

    private final Lotto lotto;
    private final Number bonusNumber;

    private WinningLotto(Lotto lotto, Number bonusNumber) {
        // TODO : validation 필요
        this.lotto = lotto;
        this.bonusNumber = bonusNumber;
    }

    public static WinningLotto of(List<Integer> numbers, int bonusNumber) {
        return new WinningLotto(Lotto.from(numbers), Number.from(bonusNumber));
    }

    public LottoPrize match(Lotto targetLotto) {
        int numberMatch = targetLotto.getMatchCount(lotto);
        boolean bonusNumberMatch = targetLotto.contains(bonusNumber);

        return LottoPrize.calculatePrize(numberMatch, bonusNumberMatch);
    }
}
