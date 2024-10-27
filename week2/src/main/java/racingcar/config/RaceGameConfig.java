package racingcar.config;

import racingcar.domain.race.RaceManager;
import racingcar.io.RaceInputHandler;
import racingcar.io.RaceOutputHandler;

public interface RaceGameConfig {

    RaceInputHandler getInputHandler();

    RaceOutputHandler getOutputHandler();

    RaceManager getRaceManager();
}
