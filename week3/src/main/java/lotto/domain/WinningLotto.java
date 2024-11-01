package lotto.domain;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import lotto.exception.BonusNumberNullException;
import lotto.exception.WinningNumberBonusNumberDuplicatedException;
import lotto.exception.WinningNumberCountInvalidException;
import lotto.exception.WinningNumbersNullException;

public class WinningLotto {

    private static final int WINNING_NUMBER_COUNT = 6;

    private final List<Number> winningNumbers;
    private final Number bonusNumber;

    private WinningLotto(List<Number> winningNumbers, Number bonusNumber) {
        validate(winningNumbers, bonusNumber);
        this.winningNumbers = winningNumbers;
        this.bonusNumber = bonusNumber;
    }

    private static void validate(List<Number> winningNumbers, Number bonusNumber) {
        if (winningNumbers == null) {
            throw new WinningNumbersNullException();
        }

        if (bonusNumber == null) {
            throw new BonusNumberNullException();
        }

        if (winningNumbers.size() != WINNING_NUMBER_COUNT) {
            throw new WinningNumberCountInvalidException(WINNING_NUMBER_COUNT);
        }

        if (hasDuplicatedNumber(winningNumbers, bonusNumber)) {
            throw new WinningNumberBonusNumberDuplicatedException();
        }
    }

    private static boolean hasDuplicatedNumber(List<Number> winningNumbers, Number bonusNumber) {
        int targetSize = winningNumbers.size() + 1;

        HashSet<Number> numbers = new HashSet<>(winningNumbers);
        numbers.add(bonusNumber);

        return numbers.size() != targetSize;
    }

    public static WinningLotto of(List<Integer> winningNumbers, int bonusNumber) {
        return new WinningLotto(Number.from(winningNumbers), Number.from(bonusNumber));
    }

    public Optional<LottoPrize> match(Lotto targetLotto) {
        int numberMatch = targetLotto.getMatchCount(winningNumbers);
        boolean bonusNumberMatch = targetLotto.contains(bonusNumber);

        return LottoPrize.calculatePrize(numberMatch, bonusNumberMatch);
    }
}
