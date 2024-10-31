package lotto.io;

import java.util.EnumMap;
import java.util.List;
import lotto.domain.Lotto;
import lotto.domain.LottoPrize;
import lotto.dto.IncomeStatics;
import lotto.dto.PrizeStatics;
import lotto.io.writer.Writer;

public class OutputHandler {

    private static final String PRIZE_STATICS_PREFIX = "당첨 통계\n---";

    private final Writer writer;

    public OutputHandler(Writer writer) {
        this.writer = writer;
    }

    public void handlePurchasedLottos(List<Lotto> lottos) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(lottos.size());
        stringBuilder.append("개를 구매했습니다.");

        for (Lotto lotto : lottos) {
            stringBuilder.append(lotto);
            stringBuilder.append("\n");
        }

        writer.writeLine(stringBuilder.toString());
    }

    public void handlePrizeStatics(PrizeStatics prizeStatics) {
        EnumMap<LottoPrize, Long> prizeCount = prizeStatics.prizeCount();
        StringBuilder stringBuilder = new StringBuilder(PRIZE_STATICS_PREFIX);

        for (LottoPrize lottoPrize : prizeCount.keySet()) {
            stringBuilder.append("\n");
            stringBuilder.append(lottoPrize.description);
            stringBuilder.append(" - ");
            stringBuilder.append(prizeCount.get(lottoPrize));
            stringBuilder.append("개");
        }

        writer.writeLine(stringBuilder.toString());
    }

    public void handleIncomeStatics(IncomeStatics incomeStatics) {
        // TODO : 구현
    }
}
