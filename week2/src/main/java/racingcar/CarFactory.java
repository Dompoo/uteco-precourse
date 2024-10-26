package racingcar;

import java.util.Arrays;
import java.util.List;

public class CarFactory {

    public List<Car> create(String carNameString) {
        return Arrays.stream(carNameString.split(","))
                .map(String::trim)
                .peek(CarFactory::validate)
                .map(Car::new)
                .toList();
    }

    private static void validate(String carName) {
        if (carName.length() > 5) {
            throw new IllegalArgumentException("자동차 이름은 5자 이하여야 합니다.");
        }
    }
}
