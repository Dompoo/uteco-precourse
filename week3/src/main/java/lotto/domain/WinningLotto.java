package lotto.domain;

import java.util.List;

public class WinningLotto {

    private final List<LottoNumber> numbers;
    private final LottoNumber bonusNumber;

    private WinningLotto(List<LottoNumber> numbers, LottoNumber bonusNumber) {
        this.numbers = numbers;
        this.bonusNumber = bonusNumber;
    }

    public static WinningLotto of(List<Integer> numbers, int bonusNumber) {
        return new WinningLotto(LottoNumber.from(numbers), LottoNumber.from(bonusNumber));
    }

    public LottoPrize match(Lotto lotto) {
        List<LottoNumber> lottoNumbers = lotto.getLottoNumbers();

        long numberMatch = this.numbers.stream()
                .filter(lottoNumbers::contains)
                .count();

        boolean bonusNumberMatch = lottoNumbers.contains(bonusNumber);

        return LottoPrize.calculatePrize((int) numberMatch, bonusNumberMatch);
    }
}
