package lotto.constants;

public enum ExceptionMessages {

    //io 영역 예외메시지
    ILLEGAL_INPUT("[ERROR] 잘못된 입력입니다."),
    ILLEGAL_NUMBER_FORMAT("[ERROR] 잘못된 숫자 형식입니다."),

    //domain 영역 예외메시지
    LOTTO_NUMBER_NOT_6("[ERROR] 로또 번호는 6개여야 합니다."),
    LOTTO_NUMBER_INVALID("[ERROR] 로또 번호는 1과 45 사이여야 합니다."),
    LOTTO_NUMBER_DUPLICATED("[ERROR] 로또 번호는 중복되면 안됩니다."),

    BONUS_NUMBER_INVALID("[ERROR] 보너스 번호는 1과 45 사이여야 합니다."),

    MONEY_AMONUT_LACK("[ERROR] 구입금액은 최소 1000원 입니다."),
    MONEY_AMONUT_NOT_MULTIPLE_OF_1000("[ERROR] 구입금액은 1000원 단위입니다."),
    ;

    public final String message;

    ExceptionMessages(String message) {
        this.message = message;
    }
}
