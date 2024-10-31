package lotto.domain;

import java.util.Arrays;

public enum LottoPrize {

    FRIST_PRIZE(6, 0, 2000000000),
    SECOND_PRIZE(5, 1, 30000000),
    THIRD_PRIZE(5, 0, 1500000),
    FOURTH_PRIZE(4, 0, 50000),
    FIFTH_PRIZE(3, 0, 5000),
    NO_PRIZE(0, 0, 0),
    ;

    private final int numberMatch;
    private final int bonusNumberMatch;
    private final int prizeMoney;

    LottoPrize(int numberMatch, int bonusNumberMatch, int prizeMoney) {
        this.numberMatch = numberMatch;
        this.bonusNumberMatch = bonusNumberMatch;
        this.prizeMoney = prizeMoney;
    }

    public static LottoPrize calculatePrize(int matchCount, boolean matchBonus) {
        if (matchCount == 5 && matchBonus) {
            return SECOND_PRIZE;
        }

        return Arrays.stream(values())
                .filter(prize -> prize.numberMatch == matchCount)
                .filter(prize -> prize.bonusNumberMatch == 0)
                .findFirst()
                .orElse(NO_PRIZE);
    }
}
