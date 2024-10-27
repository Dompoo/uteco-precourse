package racingcar;

import racingcar.config.DefaultRaceGameConfig;
import racingcar.config.RaceGameConfig;

public class Application {
    public static void main(String[] args) {
        RaceGameConfig raceGameConfig = new DefaultRaceGameConfig();
        RaceGame raceGame = new RaceGame(raceGameConfig);

        raceGame.play();
    }
}
