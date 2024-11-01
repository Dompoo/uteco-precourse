package lotto.exception;

public class WinningNumbersNullException extends IllegalArgumentException {

    private static final String MESSAGE = "[ERROR] 유효하지 않은 당첨 번호 입니다.";

    public WinningNumbersNullException() {
        super(MESSAGE);
    }
}
