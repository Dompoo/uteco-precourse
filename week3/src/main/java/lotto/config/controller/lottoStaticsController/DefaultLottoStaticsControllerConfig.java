package lotto.config.controller.lottoStaticsController;

import lotto.config.io.OutputHandlerConfig;
import lotto.controller.lottoStatics.LottoStaticsController;
import lotto.controller.lottoStatics.LottoStaticsControllerImpl;

public class DefaultLottoStaticsControllerConfig implements LottoStaticsControllerConfig {

    private final LottoStaticsController lottoStaticsController;

    public DefaultLottoStaticsControllerConfig(OutputHandlerConfig outputHandlerConfig) {
        this.lottoStaticsController = new LottoStaticsControllerImpl(outputHandlerConfig.getOutputHandler());
    }

    @Override
    public LottoStaticsController getLottoStaticsController() {
        return this.lottoStaticsController;
    }
}
