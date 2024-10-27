package racingcar;

import racingcar.dto.RaceRequest;
import racingcar.dto.RaceResult;
import racingcar.io.RaceInputHandler;
import racingcar.io.RaceOutputHandler;

public class RaceGame {

    private final RaceInputHandler inputHandler;
    private final RaceOutputHandler outputHandler;
    private final RaceManager raceManager;

    public RaceGame(RaceGameConfig raceGameConfig) {
        this.inputHandler = raceGameConfig.getInputHandler();
        this.outputHandler = raceGameConfig.getOutputHandler();
        this.raceManager = raceGameConfig.getRaceManager();
    }

    public void play() {
        RaceRequest raceRequest = inputHandler.handle();
        RaceResult result = raceManager.startRace(raceRequest);
        outputHandler.handle(result);
    }
}
