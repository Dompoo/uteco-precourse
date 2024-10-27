package racingcar.domain.car;

import java.util.Arrays;
import java.util.List;

public class CarFactory {

    public List<Car> create(String carNames) {
        return Arrays.stream(carNames.split(","))
                .map(String::trim)
                .map(Car::new)
                .toList();
    }
}
