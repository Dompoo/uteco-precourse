package lotto.io;

import java.text.DecimalFormat;
import lotto.dto.IncomeStatics;
import lotto.dto.PrizeStatics;
import lotto.dto.PurchasedLottos;

public class OutputParser {

    private static final String NEW_LINE = "\n";

    String parsePurchasedLottos(PurchasedLottos purchasedLottos) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(NEW_LINE);
        stringBuilder.append(purchasedLottos.lottos().size());
        stringBuilder.append("개를 구매했습니다.");
        stringBuilder.append(NEW_LINE);
        stringBuilder.append(getPurchasedLottoString(purchasedLottos));

        return stringBuilder.toString();
    }

    private static String getPurchasedLottoString(PurchasedLottos purchasedLottos) {
        StringBuilder stringBuilder = new StringBuilder();

        purchasedLottos.lottos().forEach(lotto -> {
            stringBuilder.append(lotto);
            stringBuilder.append(NEW_LINE);
        });

        return stringBuilder.toString();
    }

    String parsePrizeStatics(PrizeStatics prizeStatics) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(NEW_LINE);
        stringBuilder.append("당첨 통계");
        stringBuilder.append(NEW_LINE);
        stringBuilder.append("---");
        stringBuilder.append(getPrizeCountString(prizeStatics));

        return stringBuilder.toString();
    }

    private static String getPrizeCountString(PrizeStatics prizeStatics) {
        StringBuilder stringBuilder = new StringBuilder();

        prizeStatics.prizeCount().forEach((prize, count) -> {
            stringBuilder.append(NEW_LINE);
            stringBuilder.append(prize.description);
            stringBuilder.append(" - ");
            stringBuilder.append(count);
            stringBuilder.append("개");
        });

        return stringBuilder.toString();
    }

    String parseIncomeStatics(IncomeStatics incomeStatics) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("총 수익률은 ");
        stringBuilder.append(formatRate(incomeStatics.incomeRate()));
        stringBuilder.append("입니다.");

        return stringBuilder.toString();
    }

    private static String formatRate(float rate) {
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.0%");
        return decimalFormat.format(rate);
    }

    String parseException(Exception e) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(NEW_LINE);
        stringBuilder.append("[ERROR] ");
        stringBuilder.append(e.getMessage());
        stringBuilder.append(NEW_LINE);

        return stringBuilder.toString();
    }
}
