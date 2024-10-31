package lotto;

import lotto.domain.numberProvider.RandomNumberPicker;
import lotto.io.InputHandler;
import lotto.io.OutputHandler;
import lotto.io.reader.MissionUtilsReader;
import lotto.io.writer.SystemWriter;

public class Application {
    public static void main(String[] args) {
        MissionUtilsReader reader = new MissionUtilsReader();
        SystemWriter writer = new SystemWriter();
        LottoController lottoController = new LottoController(
                new InputHandler(reader, writer),
                new OutputHandler(writer),
                new RetryHandler(writer),
                new RandomNumberPicker()
        );

        lottoController.run();
    }
}
