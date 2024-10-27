package racingcar;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import racingcar.testutil.TestRaceGameConfig;
import racingcar.testutil.testdouble.ReaderFake;
import racingcar.testutil.testdouble.WriterFake;

class RaceGameTest {

    @Test
    void 자동차_경주를_시작한다() {
        //given
        ReaderFake reader = new ReaderFake();
        WriterFake writer = new WriterFake();
        TestRaceGameConfig config = new TestRaceGameConfig(reader, writer);
        RaceGame sut = new RaceGame(config);
        reader.setInput("a,b,c", "3");

        //when
        sut.play();

        //then
        Assertions.assertThat(writer.getOutput()).contains(
                """
                        
                        실행 결과 :\s
                        a : -
                        b : -
                        c : -
                        
                        a : --
                        b : --
                        c : --
                        
                        a : ---
                        b : ---
                        c : ---
                        
                        최종 우승자 : a, b, c""");
    }

}