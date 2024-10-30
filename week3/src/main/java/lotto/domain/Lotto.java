package lotto.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import lotto.constants.ExceptionMessages;
import lotto.domain.numberProvider.NumberPicker;

public class Lotto {

    private static final int MIN_NUMBER = 1;
    private static final int MAX_NUMBER = 45;
    private static final int NUMBER_COUNT = 6;
    private static final int PRICE = 1000;

    private final List<Integer> numbers;

    public Lotto(List<Integer> numbers) {
        validate(numbers);
        this.numbers = numbers;
    }

    public static List<Lotto> purchase(Money money, NumberPicker numberPicker) {
        int purchaseAmount = calculatePurchaseAmount(money);

        List<Lotto> lottos = new ArrayList<>();
        for (int i = 0; i < purchaseAmount; i++) {
            List<Integer> numbers = numberPicker.pickUniqueNumbersInRange(MIN_NUMBER, MAX_NUMBER, NUMBER_COUNT);
            lottos.add(new Lotto(numbers));
        }
        return lottos;
    }

    private static int calculatePurchaseAmount(Money money) {
        return money.getAmountDividedBy(PRICE);
    }

    private void validate(List<Integer> numbers) {
        if (numbers.size() != NUMBER_COUNT) {
            throw new IllegalArgumentException(ExceptionMessages.LOTTO_NUMBER_NOT_6.message);
        }

        numbers.forEach(number -> {
            if (!isNumberValid(number)) {
                throw new IllegalArgumentException(ExceptionMessages.LOTTO_NUMBER_INVALID.message);
            }
        });

        if (hasDuplicatedNumber(numbers)) {
            throw new IllegalArgumentException(ExceptionMessages.LOTTO_NUMBER_DUPLICATED.message);
        }
    }

    private static boolean hasDuplicatedNumber(List<Integer> numbers) {
        return Set.of(numbers).size() != numbers.size();
    }

    private static boolean isNumberValid(Integer number) {
        return MIN_NUMBER <= number && number <= MAX_NUMBER;
    }

    // TODO: 추가 기능 구현
}
