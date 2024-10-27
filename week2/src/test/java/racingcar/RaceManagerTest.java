package racingcar;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import racingcar.dto.CarStatus;
import racingcar.dto.LapResult;
import racingcar.dto.RaceRequest;
import racingcar.dto.RaceResult;
import racingcar.testutil.testdouble.MoveProviderStub;

class RaceManagerTest {

    @Test
    void 레이스를_실행한다() {
        //given
        RaceFactory raceFactory = new RaceFactory();
        MoveProviderStub moveProvider = new MoveProviderStub();
        RaceManager sut = new RaceManager(raceFactory, moveProvider);
        List<Car> cars = List.of(
                new Car("자동차 1"),
                new Car("자동차 2"),
                new Car("자동차 3")
        );
        RaceRequest raceRequest = RaceRequest.of(cars, 3);

        //when
        RaceResult result = sut.startRace(raceRequest);

        //then
        Assertions.assertThat(result.getLapResults()).contains(
                LapResult.fromCarStatuses(1, List.of(
                        CarStatus.of("자동차 1", 1),
                        CarStatus.of("자동차 2", 1),
                        CarStatus.of("자동차 3", 1)
                )),
                LapResult.fromCarStatuses(3, List.of(
                        CarStatus.of("자동차 1", 3),
                        CarStatus.of("자동차 2", 3),
                        CarStatus.of("자동차 3", 3)
                ))
        );
        Assertions.assertThat(result.getWinners()).containsExactly(
                CarStatus.of("자동차 1", 3),
                CarStatus.of("자동차 2", 3),
                CarStatus.of("자동차 3", 3)
        );
    }

}