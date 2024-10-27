package racingcar.io;

import racingcar.constants.ExceptionMessages;

public class RaceInputValidator {

    public void validateCarNames(String carNames) {
        if (carNames == null || carNames.isBlank()) {
            throw new IllegalArgumentException(ExceptionMessages.CAR_NAME_EMPTY);
        }
    }

    public void validateLapCount(int lapCount) {
        if (lapCount < 1) {
            throw new IllegalArgumentException(ExceptionMessages.LAP_COUNT_LACK);
        }
    }
}
