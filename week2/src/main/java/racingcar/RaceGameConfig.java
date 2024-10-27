package racingcar;

import racingcar.io.RaceInputHandler;
import racingcar.io.RaceOutputHandler;

public interface RaceGameConfig {

    RaceInputHandler getInputHandler();

    RaceOutputHandler getOutputHandler();

    CarFactory getCarFactory();

    RaceManager getRaceManager();
}
