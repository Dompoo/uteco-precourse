package racingcar;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import racingcar.dto.CarStatus;
import racingcar.dto.LapResult;
import racingcar.dto.RaceResult;
import racingcar.testutil.testdouble.MoveProviderStub;

class RaceTest {

    @Test
    void 레이스를_시작하면_레이스결과를_알_수_있다() {
        //given
        List<Car> cars = List.of(
                new Car("자동차 1"),
                new Car("자동차 2"),
                new Car("자동차 3")
        );
        Lap lap = new Lap(cars, new MoveProviderStub());
        Race sut = new Race(lap);

        //when
        RaceResult result = sut.start(10);

        //then
        Assertions.assertThat(result.getWinners()).containsExactly(
                CarStatus.of("자동차 1", 10),
                CarStatus.of("자동차 2", 10),
                CarStatus.of("자동차 3", 10)
        );
        Assertions.assertThat(result.getLapResults()).contains(
                LapResult.fromCarStatuses(1, List.of(
                        CarStatus.of("자동차 1", 1),
                        CarStatus.of("자동차 2", 1),
                        CarStatus.of("자동차 3", 1)
                )),
                LapResult.fromCarStatuses(10, List.of(
                        CarStatus.of("자동차 1", 10),
                        CarStatus.of("자동차 2", 10),
                        CarStatus.of("자동차 3", 10)
                ))
        );
    }

}