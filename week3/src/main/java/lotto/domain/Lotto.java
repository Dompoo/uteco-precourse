package lotto.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import lotto.domain.numberProvider.NumberPicker;
import lotto.exception.LottoNumberCountInvalidException;
import lotto.exception.LottoNumberDuplicatedException;
import lotto.exception.LottoNumberNullException;

public class Lotto {

    private static final int LOTTO_NUMBER_COUNT = 6;
    private static final int PRICE = 1000;

    private final List<Number> numbers;

    private Lotto(List<Number> numbers) {
        validate(numbers);
        this.numbers = numbers;
    }

    public static Lotto from(List<Integer> numbers) {
        return new Lotto(Number.from(numbers));
    }

    public static List<Lotto> purchase(Money money, NumberPicker numberPicker) {
        int purchaseAmount = calculatePurchaseAmount(money);

        List<Lotto> lottos = new ArrayList<>();
        for (int i = 0; i < purchaseAmount; i++) {
            List<Number> numbers = Number.createUniqueNumbers(LOTTO_NUMBER_COUNT, numberPicker);
            lottos.add(new Lotto(numbers));
        }
        return lottos;
    }

    public int getMatchCount(Lotto otherLotto) {
        return (int) this.numbers.stream()
                .filter(otherLotto.numbers::contains)
                .count();
    }

    public boolean contains(Number number) {
        return this.numbers.stream()
                .anyMatch(number::equals);
    }

    private static int calculatePurchaseAmount(Money money) {
        return money.getAmount() / PRICE;
    }

    private static void validate(List<Number> numbers) {
        if (numbers == null) {
            throw new LottoNumberNullException();
        }

        if (numbers.size() != LOTTO_NUMBER_COUNT) {
            throw new LottoNumberCountInvalidException(LOTTO_NUMBER_COUNT);
        }

        if (hasDuplicatedNumber(numbers)) {
            throw new LottoNumberDuplicatedException();
        }
    }

    private static boolean hasDuplicatedNumber(List<Number> numbers) {
        return new HashSet<>(numbers).size() != numbers.size();
    }
}
