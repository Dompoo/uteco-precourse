package lotto.io;

import java.util.List;
import lotto.io.reader.Reader;
import lotto.io.writer.Writer;

public class InputHandler {

    private static final String PURCHASE_AMONUT_DESCRIPTION = "구입금액을 입력해 주세요.";
    private static final String WINNING_LOTTO_NUMBERS_DESCRPTION = "당첨 번호를 입력해 주세요.";
    private static final String WINNING_LOTTO_NUMBERS_SEPARATOR = ",";
    private static final String WINNING_LOTTO_BONUS_NUMBER_DESCRPTION = "보너스 번호를 입력해 주세요.";

    private final Reader reader;
    private final Writer writer;

    public InputHandler(Reader reader, Writer writer) {
        this.reader = reader;
        this.writer = writer;
    }

    public int handlePurchaseAmount() {
        writer.writeLine(PURCHASE_AMONUT_DESCRIPTION);
        return reader.readLineAsNumber();
    }

    public List<Integer> handleWinningLottoNumbers() {
        writer.writeLine(WINNING_LOTTO_NUMBERS_DESCRPTION);
        return reader.readLineAsNumbers(WINNING_LOTTO_NUMBERS_SEPARATOR);
    }

    public int handleWinningLottoBonusNumber() {
        writer.writeLine(WINNING_LOTTO_BONUS_NUMBER_DESCRPTION);
        return reader.readLineAsNumber();
    }
}
