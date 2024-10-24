package racingcar;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import racingcar.dto.CarStatus;
import racingcar.dto.LapResult;
import racingcar.testutil.testdouble.MoveProviderStub;

class LapTest {

    @Test
    void 경주_결과를_반환한다() {
        //given
        Lap sut = new Lap(getCars(), new MoveProviderStub());

        //when
        LapResult result = sut.oneLap();

        //then
        Assertions.assertThat(result.getCarStatuses()).contains(
                CarStatus.of("자동차 1", 1),
                CarStatus.of("자동차 2", 1),
                CarStatus.of("자동차 3", 1)
        );
    }

    private List<Car> getCars() {
        return List.of(
                new Car("자동차 1"),
                new Car("자동차 2"),
                new Car("자동차 3")
        );
    }

}