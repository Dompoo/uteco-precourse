package lotto.aop;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import lotto.aop.RetryHandler.IllegalArgumentExceptionThrower;
import lotto.io.OutputHandler;
import lotto.io.OutputParser;
import lotto.testUtil.testDouble.WriterFake;
import org.junit.jupiter.api.Test;

class RetryHandlerTest {

    @Test
    void 열번_미만으로_시도하고_성공하면_값을_반환한다() {
        //given
        WriterFake writerFake = new WriterFake();
        OutputHandler outputHandler = new OutputHandler(writerFake, new OutputParser());
        RetryHandler sut = new RetryHandler(outputHandler);
        IllegalArgumentExceptionThrower<Integer> thrower = new IllegalArgumentExceptionThrower<>() {
            int i = 0;

            @Override
            public Integer run() throws IllegalArgumentException {
                if (i++ < 9) {
                    throw new IllegalArgumentException();
                } else {
                    return 100;
                }
            }
        };

        //when
        Integer result = sut.tryUntilSuccess(thrower);

        //then
        assertThat(result).isEqualTo(100);
    }

    @Test
    void 열번_이상으로_시도하면_예외가_발생한다() {
        //given
        WriterFake writerFake = new WriterFake();
        OutputHandler outputHandler = new OutputHandler(writerFake, new OutputParser());
        RetryHandler sut = new RetryHandler(outputHandler);
        IllegalArgumentExceptionThrower<Integer> thrower = new IllegalArgumentExceptionThrower<>() {
            int i = 0;

            @Override
            public Integer run() throws IllegalArgumentException {
                if (i++ < 10) {
                    throw new IllegalArgumentException();
                } else {
                    return 100;
                }
            }
        };

        //expected
        assertThatThrownBy(() -> sut.tryUntilSuccess(thrower))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("최대 시도 횟수를 초과했습니다.");
    }
}
