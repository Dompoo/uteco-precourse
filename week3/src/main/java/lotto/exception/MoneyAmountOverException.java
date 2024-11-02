package lotto.exception;

public class MoneyAmountOverException extends IllegalArgumentException {

    private static final String MESSAGE = "구입금액은 최대 %d원 입니다.";

    public MoneyAmountOverException(int maxMoneyAmount) {
        super(String.format(MESSAGE, maxMoneyAmount));
    }
}
