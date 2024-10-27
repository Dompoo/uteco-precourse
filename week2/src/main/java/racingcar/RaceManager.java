package racingcar;

import java.util.List;
import racingcar.dto.RaceRequest;
import racingcar.dto.RaceResult;
import racingcar.moveProvider.MoveProvider;

public class RaceManager {

    private final CarFactory carFactory;
    private final RaceFactory raceFactory;
    private final MoveProvider moveProvider;

    public RaceManager(CarFactory carFactory, RaceFactory raceFactory, MoveProvider moveProvider) {
        this.carFactory = carFactory;
        this.raceFactory = raceFactory;
        this.moveProvider = moveProvider;
    }

    public RaceResult startRace(RaceRequest raceRequest) {
        List<Car> cars = carFactory.create(raceRequest.getCarNames());
        Race race = raceFactory.create(cars, moveProvider);

        return race.start(raceRequest.getLapCount());
    }
}
