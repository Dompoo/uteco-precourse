package racingcar.io;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import racingcar.domain.car.Car;
import racingcar.domain.car.CarFactory;

class CarFactoryTest {

    @Test
    void 자동차_이름_문자열을_받아서_자동차를_생성한다() {
        //given
        String carNameString = "hello,world";
        CarFactory sut = new CarFactory();

        //when
        List<Car> result = sut.create(carNameString);

        //then
        Assertions.assertThat(result)
                .extracting("name")
                .containsExactlyInAnyOrder("hello", "world");
    }

    @Test
    void 앞뒤로_공백이_있으면_제거하고_생성한다() {
        //given
        String carNameString = "    hello    ,    world    ";
        CarFactory sut = new CarFactory();

        //when
        List<Car> result = sut.create(carNameString);

        //then
        Assertions.assertThat(result)
                .extracting("name")
                .containsExactlyInAnyOrder("hello", "world");
    }

    @Test
    void 중간_공백은_제거하지_않고_생성한다() {
        //given
        String carNameString = "ha lo,ja va";
        CarFactory sut = new CarFactory();

        //when
        List<Car> result = sut.create(carNameString);

        //then
        Assertions.assertThat(result)
                .extracting("name")
                .containsExactlyInAnyOrder("ha lo", "ja va");
    }

    @Test
    void 자동차_이름이_5자가_넘어가면_예외가_발생한다() {
        //given
        String carNameString = "halllo";
        CarFactory sut = new CarFactory();

        //expected
        Assertions.assertThatThrownBy(() ->
                        sut.create(carNameString))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessage("자동차 이름은 5자 이하여야 합니다.");
    }

}