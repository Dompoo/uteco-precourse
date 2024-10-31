package lotto.exception;

public class OverMaxRetryAttempt extends IllegalStateException {

    private static final String MESSAGE = "[ERROR] 최대 시도 횟수를 초과했습니다.";

    public OverMaxRetryAttempt() {
        super(MESSAGE);
    }
}
