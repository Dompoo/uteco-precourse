package racingcar;

import racingcar.io.RaceInputHandler;
import racingcar.io.RaceInputValidator;
import racingcar.io.RaceOutputHandler;
import racingcar.io.RaceResultParser;
import racingcar.io.reader.MissionUtilsReader;
import racingcar.io.reader.Reader;
import racingcar.io.writer.SystemWriter;
import racingcar.io.writer.Writer;
import racingcar.moveProvider.MoveProvider;
import racingcar.moveProvider.RandomMoveProvider;
import racingcar.randomProvider.MissionUtilsRandomAdapter;
import racingcar.randomProvider.RandomAdapter;

public class DefaultRaceGameConfig implements RaceGameConfig {

    @Override
    public RaceInputHandler getInputHandler() {
        return new RaceInputHandler(getReader(), getWriter(), getRaceInputValidator());
    }

    @Override
    public RaceOutputHandler getOutputHandler() {
        return new RaceOutputHandler(getWriter(), getRaceResultParser());
    }

    @Override
    public CarFactory getCarFactory() {
        return new CarFactory();
    }

    @Override
    public RaceManager getRaceManager() {
        return new RaceManager(getRaceFactory(), getMoveProvider());
    }

    private Reader getReader() {
        return new MissionUtilsReader();
    }

    private Writer getWriter() {
        return new SystemWriter();
    }

    private RaceInputValidator getRaceInputValidator() {
        return new RaceInputValidator();
    }

    private RaceResultParser getRaceResultParser() {
        return new RaceResultParser();
    }

    private RaceFactory getRaceFactory() {
        return new RaceFactory();
    }

    private MoveProvider getMoveProvider() {
        return new RandomMoveProvider(getRandomAdapter());
    }

    private RandomAdapter getRandomAdapter() {
        return new MissionUtilsRandomAdapter();
    }
}
