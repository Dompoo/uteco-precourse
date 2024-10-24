package racingcar.dto;

import java.util.List;
import java.util.Objects;
import racingcar.Car;

public class LapResult {

    private final List<CarStatus> carStatuses;

    private LapResult(List<CarStatus> carStatuses) {
        this.carStatuses = carStatuses;
    }

    public static LapResult from(List<Car> cars) {
        return new LapResult(cars.stream()
                .map(CarStatus::from)
                .toList());
    }

    public List<CarStatus> getCarStatuses() {
        return carStatuses;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (CarStatus carStatus : carStatuses) {
            sb.append(carStatus);
            sb.append("\n");
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LapResult result)) {
            return false;
        }
        return Objects.equals(getCarStatuses(), result.getCarStatuses());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getCarStatuses());
    }
}
