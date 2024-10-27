package racingcar.io;

public class RaceInputDto {

    private final String carNames;
    private final int lapCount;

    private RaceInputDto(String carNames, int lapCount) {
        this.carNames = carNames;
        this.lapCount = lapCount;
    }

    public static RaceInputDto of(String carNames, int lapCount) {
        return new RaceInputDto(carNames, lapCount);
    }

    public String getCarNames() {
        return carNames;
    }

    public int getLapCount() {
        return lapCount;
    }
}
