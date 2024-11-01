package lotto.exception;

public class MoneyAmountLackException extends IllegalArgumentException {

    private static final String MESSAGE = "구입금액은 최소 %d원 입니다.";

    public MoneyAmountLackException(int minMoneyAmount) {
        super(String.format(MESSAGE, minMoneyAmount));
    }
}
