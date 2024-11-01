package lotto.io;

import lotto.dto.IncomeStatics;
import lotto.dto.PrizeStatics;
import lotto.dto.PurchasedLottoDto;
import lotto.io.writer.Writer;

public class OutputHandler {

    private final Writer writer;
    private final OutputParser outputParser;

    public OutputHandler(Writer writer, OutputParser outputParser) {
        this.writer = writer;
        this.outputParser = outputParser;
    }

    public void handlePurchasedLottos(PurchasedLottoDto purchasedLottoDto) {
        String message = outputParser.parsePurchasedLottos(purchasedLottoDto);
        writer.writeLine(message);
    }

    public void handlePrizeStatics(PrizeStatics prizeStatics) {
        String message = outputParser.parsePrizeStatics(prizeStatics);
        writer.writeLine(message);
    }

    public void handleIncomeStatics(IncomeStatics incomeStatics) {
        String message = outputParser.parseIncomeStatics(incomeStatics);
        writer.writeLine(message);
    }

    public void handleException(Exception exception) {
        String message = outputParser.parseException(exception);
        writer.writeLine(message);
    }
}
