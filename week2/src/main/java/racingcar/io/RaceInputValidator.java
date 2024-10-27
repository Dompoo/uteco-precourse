package racingcar.io;

public class RaceInputValidator {

    public void validateCarNames(String carNames) {
        if (carNames == null || carNames.isBlank()) {
            throw new IllegalArgumentException("자동차 이름은 비어있을 수 없습니다.");
        }
    }

    public void validateLapCount(int lapCount) {
        if (lapCount < 1) {
            throw new IllegalArgumentException("시도는 1회 이상이어야 합니다.");
        }
    }
}
