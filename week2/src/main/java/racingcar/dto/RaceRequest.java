package racingcar.dto;

import java.util.List;
import java.util.Objects;
import racingcar.Car;

public class RaceRequest {

    private final List<Car> cars;
    private final int lapCount;

    private RaceRequest(List<Car> cars, int lapCount) {
        this.cars = cars;
        this.lapCount = lapCount;
    }

    public static RaceRequest of(List<Car> cars, int lapCount) {
        return new RaceRequest(cars, lapCount);
    }

    public List<Car> getCars() {
        return cars;
    }

    public int getLapCount() {
        return lapCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RaceRequest that)) {
            return false;
        }
        return lapCount == that.lapCount && Objects.equals(cars, that.cars);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cars, lapCount);
    }
}
