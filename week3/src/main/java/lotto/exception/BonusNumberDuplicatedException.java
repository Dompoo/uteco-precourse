package lotto.exception;

public class BonusNumberDuplicatedException extends IllegalArgumentException {

    private static final String MESSAGE = "[ERROR] 보너스 번호가 당첨 번호와 중복되었습니다.";

    public BonusNumberDuplicatedException() {
        super(MESSAGE);
    }
}
