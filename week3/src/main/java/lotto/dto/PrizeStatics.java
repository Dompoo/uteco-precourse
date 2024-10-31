package lotto.dto;

import java.util.EnumMap;
import lotto.domain.LottoPrize;

public record PrizeStatics(
        EnumMap<LottoPrize, Long> prizeCount
) {
}
