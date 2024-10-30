package lotto.constants;

public enum ExceptionMessages {

    ILLEGAL_INPUT("[ERROR] 잘못된 입력입니다."),
    ILLEGAL_NUMBER_FORMAT("[ERROR] 잘못된 숫자 형식입니다."),
    ;

    public final String message;

    ExceptionMessages(String message) {
        this.message = message;
    }
}
