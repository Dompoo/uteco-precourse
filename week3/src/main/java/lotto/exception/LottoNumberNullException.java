package lotto.exception;

public class LottoNumberNullException extends IllegalArgumentException {

    private static final String MESSAGE = "유효하지 않은 로또 번호입니다.";

    public LottoNumberNullException() {
        super(MESSAGE);
    }
}
