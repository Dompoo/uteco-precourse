package racingcar;

import racingcar.dto.RaceRequest;
import racingcar.dto.RaceResult;
import racingcar.moveProvider.MoveProvider;

public class RaceManager {

    private final RaceFactory raceFactory;
    private final MoveProvider moveProvider;

    public RaceManager(RaceFactory raceFactory, MoveProvider moveProvider) {
        this.raceFactory = raceFactory;
        this.moveProvider = moveProvider;
    }

    public RaceResult startRace(RaceRequest raceRequest) {
        Race race = raceFactory.create(raceRequest.getCars(), moveProvider);

        return race.start(raceRequest.getLapCount());
    }
}
