package lotto.config;

import lotto.controller.LottoController;
import lotto.controller.LottoControllerImpl;
import lotto.controller.RetryFilter;

public class LottoControllerConfig {

    private final LottoController lottoController;

    public LottoControllerConfig(LottoConfig lottoConfig) {
        LottoControllerImpl lottoController = new LottoControllerImpl(lottoConfig);
        this.lottoController = new RetryFilter(lottoController, lottoConfig.getRetryHandler());
    }

    public LottoController getLottoController() {
        return lottoController;
    }
}
