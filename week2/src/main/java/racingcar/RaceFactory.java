package racingcar;

import java.util.List;
import racingcar.moveProvider.MoveProvider;

public class RaceFactory {

    public Race create(List<Car> cars, MoveProvider moveProvider) {
        Lap lap = new Lap(cars, moveProvider);
        return new Race(lap);
    }
}
