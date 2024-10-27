package racingcar.testutil;

import racingcar.domain.car.CarFactory;
import racingcar.domain.race.RaceFactory;
import racingcar.config.RaceGameConfig;
import racingcar.domain.race.RaceManager;
import racingcar.io.RaceInputHandler;
import racingcar.io.RaceInputValidator;
import racingcar.io.RaceOutputHandler;
import racingcar.io.RaceResultParser;
import racingcar.io.reader.Reader;
import racingcar.io.writer.Writer;
import racingcar.testutil.testdouble.MoveProviderStub;

public class TestRaceGameConfig implements RaceGameConfig {

    private final Reader reader;
    private final Writer writer;

    public TestRaceGameConfig(Reader reader, Writer writer) {
        this.reader = reader;
        this.writer = writer;
    }

    @Override
    public RaceInputHandler getInputHandler() {
        return new RaceInputHandler(reader, writer, new RaceInputValidator());
    }

    @Override
    public RaceOutputHandler getOutputHandler() {
        return new RaceOutputHandler(writer, new RaceResultParser());
    }

    @Override
    public RaceManager getRaceManager() {
        return new RaceManager(new CarFactory(), new RaceFactory(), new MoveProviderStub());
    }
}
