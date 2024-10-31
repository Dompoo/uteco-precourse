package lotto.dto;

import java.util.List;
import lotto.domain.Lotto;

public record PurchasedLottoDto(
        List<String> lottos
) {
    public static PurchasedLottoDto from(List<Lotto> lottos) {
        return new PurchasedLottoDto(lottos.stream()
                .map(Lotto::toString)
                .toList());
    }
}
