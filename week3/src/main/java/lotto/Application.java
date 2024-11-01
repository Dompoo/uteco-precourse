package lotto;

import lotto.config.ApplicationConfig;
import lotto.controller.LottoApplication;

public class Application {

    public static void main(String[] args) {
        ApplicationConfig applicationConfig = new ApplicationConfig();

        LottoApplication lottoApplication = applicationConfig.getLottoApplication();
        lottoApplication.run();
    }
}
