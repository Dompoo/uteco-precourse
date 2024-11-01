package lotto.exception;

public class LottoNumberDuplicatedException extends IllegalArgumentException {

    private static final String MESSAGE = "중복된 로또 번호입니다.";

    public LottoNumberDuplicatedException() {
        super(MESSAGE);
    }
}
