package lotto.config.numberPricker;

import lotto.domain.numberProvider.NumberPicker;
import lotto.domain.numberProvider.RandomNumberPicker;

public class DefaultNumberPickerConfig implements NumberPickerConfig {

    private final NumberPicker numberPicker;

    public DefaultNumberPickerConfig() {
        this.numberPicker = new RandomNumberPicker();
    }

    @Override
    public NumberPicker getNumberPicker() {
        return this.numberPicker;
    }
}
