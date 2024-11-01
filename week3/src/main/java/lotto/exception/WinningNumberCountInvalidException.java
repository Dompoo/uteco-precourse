package lotto.exception;

public class WinningNumberCountInvalidException extends IllegalArgumentException {

    private static final String MESSAGE = "[ERROR] 당첨 번호는 %d개여야 합니다.";

    public WinningNumberCountInvalidException(int validCount) {
        super(String.format(MESSAGE, validCount));
    }
}
