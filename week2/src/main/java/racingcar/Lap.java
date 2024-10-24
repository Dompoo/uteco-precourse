package racingcar;

import java.util.List;
import racingcar.dto.LapResult;
import racingcar.moveProvider.MoveProvider;

public class Lap {

    private static int lapNumber = 1;
    private final List<Car> cars;
    private final MoveProvider moveProvider;

    public Lap(List<Car> cars, MoveProvider moveProvider) {
        this.cars = cars;
        this.moveProvider = moveProvider;
    }

    public LapResult oneLap() {
        cars.forEach(car -> car.move(moveProvider.canMove()));
        return LapResult.fromCars(lapNumber++, cars);
    }
}
