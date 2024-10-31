package lotto.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import lotto.domain.numberProvider.NumberPicker;
import lotto.exception.LottoNumberCountInvalidException;
import lotto.exception.LottoNumberDuplicatedException;

public class Lotto {

    private static final int LOTTO_NUMBER_COUNT = 6;
    private static final int PRICE = 1000;

    private final List<LottoNumber> lottoNumbers;

    private Lotto(List<LottoNumber> lottoNumbers) {
        validate(lottoNumbers);
        this.lottoNumbers = lottoNumbers;
    }

    public static List<Lotto> purchase(Money money, NumberPicker numberPicker) {
        int purchaseAmount = calculatePurchaseAmount(money);

        List<Lotto> lottos = new ArrayList<>();
        for (int i = 0; i < purchaseAmount; i++) {
            List<LottoNumber> lottoNumbers = LottoNumber.createUniqueLottoNumbers(LOTTO_NUMBER_COUNT, numberPicker);
            lottos.add(new Lotto(lottoNumbers));
        }
        return lottos;
    }

    private static int calculatePurchaseAmount(Money money) {
        return money.getAmountDividedBy(PRICE);
    }

    private void validate(List<LottoNumber> lottoNumbers) {
        if (lottoNumbers.size() != LOTTO_NUMBER_COUNT) {
            throw new LottoNumberCountInvalidException(LOTTO_NUMBER_COUNT);
        }

        if (hasDuplicatedNumber(lottoNumbers)) {
            throw new LottoNumberDuplicatedException();
        }
    }

    private static boolean hasDuplicatedNumber(List<LottoNumber> lottoNumbers) {
        return Set.of(lottoNumbers).size() != lottoNumbers.size();
    }
}
