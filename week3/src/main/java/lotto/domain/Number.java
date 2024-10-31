package lotto.domain;

import java.util.List;
import java.util.Objects;
import lotto.domain.numberProvider.NumberPicker;
import lotto.exception.LottoNumberInvalidException;

public class Number implements Comparable<Number> {

    private static final int MIN_VALUE = 1;
    private static final int MAX_VALUE = 45;

    private final int value;

    private Number(int value) {
        validate(value);
        this.value = value;
    }

    public static Number from(int number) {
        return new Number(number);
    }

    public static List<Number> from(List<Integer> numbers) {
        return numbers.stream()
                .map(Number::new)
                .toList();
    }

    public static List<Number> createUniqueNumbers(int count, NumberPicker numberPicker) {
        List<Integer> numbers = numberPicker.pickUniqueNumbersInRange(MIN_VALUE, MAX_VALUE, count);

        return from(numbers);
    }

    private static void validate(int value) {
        if (!(MIN_VALUE <= value && value <= MAX_VALUE)) {
            throw new LottoNumberInvalidException(MIN_VALUE, MAX_VALUE);
        }
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Number number)) {
            return false;
        }
        return this.value == number.value;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    @Override
    public int compareTo(Number o) {
        return this.value - o.value;
    }
}
