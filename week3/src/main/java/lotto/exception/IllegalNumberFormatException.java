package lotto.exception;

public class IllegalNumberFormatException extends IllegalArgumentException {

    private static final String MESSAGE = "[ERROR] 잘못된 숫자 형식입니다.";

    public IllegalNumberFormatException() {
        super(MESSAGE);
    }
}
