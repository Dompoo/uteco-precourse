package lotto.exception;

public class LottoNumberCountInvalidException extends IllegalArgumentException {

    private static final String MESSAGE = "[ERROR] 로또 번호는 %d개여야 합니다.";

    public LottoNumberCountInvalidException(int validCount) {
        super(String.format(MESSAGE, validCount));
    }
}