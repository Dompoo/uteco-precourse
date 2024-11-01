package lotto.exception;

public class MoneyNullException extends IllegalArgumentException {

    private static final String MESSAGE = "유효하지 않은 구입 금액입니다.";

    public MoneyNullException() {
        super(MESSAGE);
    }
}
