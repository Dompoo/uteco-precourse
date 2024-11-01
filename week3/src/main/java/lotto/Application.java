package lotto;

import lotto.config.LottoConfig;
import lotto.config.numberPricker.DefaultNumberPickerConfig;
import lotto.config.reader.DefaultReaderConfig;
import lotto.config.writer.DefaultWriterConfig;
import lotto.controller.ControllerFacade;

public class Application {

    public static void main(String[] args) {
        LottoConfig lottoConfig = new LottoConfig(
                new DefaultReaderConfig(),
                new DefaultWriterConfig(),
                new DefaultNumberPickerConfig()
        );

        ControllerFacade controller = new ControllerFacade(lottoConfig);

        controller.run();
    }
}
