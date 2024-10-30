package lotto.domain;

import java.util.List;
import java.util.Objects;
import lotto.constants.ExceptionMessages;
import lotto.domain.numberProvider.NumberPicker;

public class LottoNumber {

    private static final int MIN_VALUE = 1;
    private static final int MAX_VALUE = 45;

    private final int value;

    private LottoNumber(int value) {
        validate(value);
        this.value = value;
    }

    public static List<LottoNumber> from(List<Integer> numbers) {
        return numbers.stream()
                .map(LottoNumber::new)
                .toList();
    }

    public static List<LottoNumber> createUniqueLottoNumbers(int count, NumberPicker numberPicker) {
        List<Integer> numbers = numberPicker.pickUniqueNumbersInRange(MIN_VALUE, MAX_VALUE, count);

        return from(numbers);
    }

    public int getValue() {
        return value;
    }

    private static void validate(int value) {
        if (!(MIN_VALUE <= value && value <= MAX_VALUE)) {
            throw new IllegalArgumentException(ExceptionMessages.NUMBER_INVALID.message);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LottoNumber lottoNumber)) {
            return false;
        }
        return getValue() == lottoNumber.getValue();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getValue());
    }
}
