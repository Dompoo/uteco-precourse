package lotto.exception;

public class IllegalInputException extends IllegalArgumentException {

    private static final String MESSAGE = "잘못된 입력입니다.";

    public IllegalInputException() {
        super(MESSAGE);
    }
}
