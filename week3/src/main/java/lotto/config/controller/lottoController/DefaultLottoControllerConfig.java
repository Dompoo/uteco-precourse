package lotto.config.controller.lottoController;

import lotto.config.io.OutputHandlerConfig;
import lotto.config.numberPricker.NumberPickerConfig;
import lotto.controller.lotto.LottoController;
import lotto.controller.lotto.LottoControllerImpl;

public class DefaultLottoControllerConfig implements LottoControllerConfig {

    private final LottoController lottoController;

    public DefaultLottoControllerConfig(
            OutputHandlerConfig outputHandlerConfig,
            NumberPickerConfig numberPickerConfig
    ) {
        this.lottoController = new LottoControllerImpl(
                outputHandlerConfig.getOutputHandler(),
                numberPickerConfig.getNumberPicker()
        );
    }

    @Override
    public LottoController getLottoController() {
        return this.lottoController;
    }
}
