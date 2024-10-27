package racingcar.config;

import racingcar.domain.race.RaceManager;
import racingcar.domain.car.CarFactory;
import racingcar.domain.race.RaceFactory;
import racingcar.domain.moveProvider.MoveProvider;
import racingcar.domain.moveProvider.RandomMoveProvider;
import racingcar.domain.randomProvider.MissionUtilsRandomAdapter;
import racingcar.domain.randomProvider.RandomAdapter;
import racingcar.io.RaceInputHandler;
import racingcar.io.RaceInputValidator;
import racingcar.io.RaceOutputHandler;
import racingcar.io.RaceResultParser;
import racingcar.io.reader.MissionUtilsReader;
import racingcar.io.reader.Reader;
import racingcar.io.writer.SystemWriter;
import racingcar.io.writer.Writer;

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
    public RaceManager getRaceManager() {
        return new RaceManager(getCarFactory(), getRaceFactory(), getMoveProvider());
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

    private CarFactory getCarFactory() {
        return new CarFactory();
    }

    private RandomAdapter getRandomAdapter() {
        return new MissionUtilsRandomAdapter();
    }
}
