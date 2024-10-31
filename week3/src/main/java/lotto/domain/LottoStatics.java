package lotto.domain;

import java.util.EnumMap;
import java.util.List;
import java.util.stream.Collectors;
import lotto.exception.LottoNullException;
import lotto.exception.MoneyNullException;
import lotto.exception.WinningLottoNullException;

public class LottoStatics {

    private final EnumMap<LottoPrize, Long> prizeCount;
    private final Money money;

    private LottoStatics(List<Lotto> lottos, WinningLotto winningLotto, Money money) {
        validate(lottos, winningLotto, money);
        this.prizeCount = calculatePrizeCount(lottos, winningLotto);
        this.money = money;
    }

    public static LottoStatics of(List<Lotto> lottos, WinningLotto winningLotto, Money money) {
        return new LottoStatics(lottos, winningLotto, money);
    }

    private static EnumMap<LottoPrize, Long> calculatePrizeCount(List<Lotto> lottos, WinningLotto winningLotto) {
        return lottos.stream()
                .map(winningLotto::match)
                .collect(Collectors.groupingBy(
                        rank -> rank,
                        () -> new EnumMap<>(LottoPrize.class),
                        Collectors.counting()
                ));
    }

    private static void validate(List<Lotto> lottos, WinningLotto winningLotto, Money money) {
        if (lottos == null) {
            throw new LottoNullException();
        }

        if (winningLotto == null) {
            throw new WinningLottoNullException();
        }

        if (money == null) {
            throw new MoneyNullException();
        }
    }
}
