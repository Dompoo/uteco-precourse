package lotto.domain;

import lotto.exception.MoneyAmountLackException;
import lotto.exception.MoneyUnitInvalidException;

public class Money {

    private static final int MIN_MONEY = 1000;
    private static final int MONEY_UNIT = 1000;

    private final int amount;

    private Money(int amount) {
        validate(amount);
        this.amount = amount;
    }

    public static Money from(int amount) {
        return new Money(amount);
    }

    public int getAmount() {
        return amount;
    }

    private static void validate(int amount) {
        if (amount < 1000) {
            throw new MoneyAmountLackException(MIN_MONEY);
        }
        if (amount % 1000 != 0) {
            throw new MoneyUnitInvalidException(MONEY_UNIT);
        }
    }

    // TODO : 추가 기능 구현
}
