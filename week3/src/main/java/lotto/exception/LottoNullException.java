package lotto.exception;

public class LottoNullException extends IllegalArgumentException {

    private static final String MESSAGE = "유효하지 않은 로또입니다.";

    public LottoNullException() {
        super(MESSAGE);
    }
}
