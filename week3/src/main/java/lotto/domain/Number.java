package lotto.domain;

import java.util.List;
import java.util.Objects;
import lotto.constants.ExceptionMessages;
import lotto.domain.numberProvider.NumberPicker;

public class Number {

    private static final int MIN_VALUE = 1;
    private static final int MAX_VALUE = 45;

    private final int value;

    private Number(int value) {
        validate(value);
        this.value = value;
    }

    public static List<Number> createUniqueNumbers(int count, NumberPicker numberPicker) {
        return numberPicker.pickUniqueNumbersInRange(MIN_VALUE, MAX_VALUE, count).stream()
                .map(Number::new)
                .toList();
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
        if (!(o instanceof Number number)) {
            return false;
        }
        return getValue() == number.getValue();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getValue());
    }
}
