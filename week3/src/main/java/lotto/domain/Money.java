package lotto.domain;

import lotto.constants.ExceptionMessages;

public class Money {

    private final int amount;

    public Money(int amount) {
        validate(amount);
        this.amount = amount;
    }

    private void validate(int amount) {
        if (amount < 1000) {
            throw new IllegalArgumentException(ExceptionMessages.MONEY_AMONUT_LACK.message);
        }
        if (amount % 1000 != 0) {
            throw new IllegalArgumentException(ExceptionMessages.MONEY_AMONUT_NOT_MULTIPLE_OF_1000.message);
        }
    }

    // TODO : 추가 기능 구현
}
