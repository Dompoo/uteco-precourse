package lotto.domain;

import java.util.List;
import lotto.constants.ExceptionMessages;

public class Lotto {

    private final List<Integer> numbers;

    public Lotto(List<Integer> numbers) {
        validate(numbers);
        this.numbers = numbers;
    }

    private void validate(List<Integer> numbers) {
        if (numbers.size() != 6) {
            throw new IllegalArgumentException(ExceptionMessages.LOTTO_NUMBER_NOT_6.message);
        }

        numbers.forEach(number -> {
            if (!isNumberValid(number)) {
                throw new IllegalArgumentException(ExceptionMessages.LOTTO_NUMBER_NOT_VALID.message);
            }
        });
    }

    private boolean isNumberValid(Integer number) {
        return 1 <= number && number <= 45;
    }

    // TODO: 추가 기능 구현
}
