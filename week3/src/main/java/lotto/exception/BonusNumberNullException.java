package lotto.exception;

public class BonusNumberNullException extends IllegalArgumentException {

    private static final String MESSAGE = "[ERROR] 유효하지 않은 보너스 번호입니다.";

    public BonusNumberNullException() {
        super(MESSAGE);
    }
}
