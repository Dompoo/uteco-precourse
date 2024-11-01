package lotto.exception;

public class WinningNumberBonusNumberDuplicatedException extends IllegalArgumentException {

    private static final String MESSAGE = "[ERROR] 당첨 번호와 보너스 번호에 중복이 있습니다.";

    public WinningNumberBonusNumberDuplicatedException() {
        super(MESSAGE);
    }
}
