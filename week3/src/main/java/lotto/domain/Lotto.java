package lotto.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import lotto.constants.ExceptionMessages;
import lotto.domain.numberProvider.NumberPicker;

public class Lotto {

    private static final int NUMBER_COUNT = 6;
    private static final int PRICE = 1000;

    private final List<Number> numbers;

    private Lotto(List<Number> numbers) {
        validate(numbers);
        this.numbers = numbers;
    }

    public static List<Lotto> purchase(Money money, NumberPicker numberPicker) {
        int purchaseAmount = calculatePurchaseAmount(money);

        List<Lotto> lottos = new ArrayList<>();
        for (int i = 0; i < purchaseAmount; i++) {
            List<Number> numbers = Number.createUniqueNumbers(NUMBER_COUNT, numberPicker);
            lottos.add(new Lotto(numbers));
        }
        return lottos;
    }

    private static int calculatePurchaseAmount(Money money) {
        return money.getAmountDividedBy(PRICE);
    }

    private void validate(List<Number> numbers) {
        if (numbers.size() != NUMBER_COUNT) {
            throw new IllegalArgumentException(ExceptionMessages.LOTTO_NUMBER_NOT_6.message);
        }

        if (hasDuplicatedNumber(numbers)) {
            throw new IllegalArgumentException(ExceptionMessages.LOTTO_NUMBER_DUPLICATED.message);
        }
    }

    private static boolean hasDuplicatedNumber(List<Number> numbers) {
        return Set.of(numbers).size() != numbers.size();
    }

    // TODO: 추가 기능 구현
}
