package racingcar.domain.car;

import java.util.Arrays;
import java.util.List;
import racingcar.constants.ExceptionMessages;

public class CarFactory {

    public List<Car> create(String carNames) {
        return Arrays.stream(carNames.split(","))
                .map(String::trim)
                .peek(CarFactory::validate)
                .map(Car::new)
                .toList();
    }

    private static void validate(String carName) {
        if (carName.length() > 5) {
            throw new IllegalArgumentException(ExceptionMessages.CAR_NAME_LENGTH_EXCEED);
        }
    }
}
