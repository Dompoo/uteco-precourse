package lotto.exception;

public class WinningLottoNullException extends IllegalArgumentException {

    private static final String MESSAGE = "유효하지 않은 당첨 로또입니다.";

    public WinningLottoNullException() {
        super(MESSAGE);
    }
}
