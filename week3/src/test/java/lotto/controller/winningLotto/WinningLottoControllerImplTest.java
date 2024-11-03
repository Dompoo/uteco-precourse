package lotto.controller.winningLotto;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import lotto.domain.Lotto;
import lotto.domain.LottoPrize;
import lotto.domain.Number;
import lotto.domain.WinningLotto;
import lotto.testUtil.testDouble.InputHandlerStub;
import org.junit.jupiter.api.Test;

class WinningLottoControllerImplTest {

    @Test
    void 당첨번호를_반환한다() {
        //given
        InputHandlerStub inputHandlerStub = new InputHandlerStub();
        WinningLottoControllerImpl sut = new WinningLottoControllerImpl(inputHandlerStub);
        inputHandlerStub.stubWinningNumbers(1, 2, 3, 4, 5, 6);

        //when
        Lotto result = sut.getWinningNumbers();

        //then
        assertThat(result.contains(Number.from(1))).isTrue();
        assertThat(result.contains(Number.from(2))).isTrue();
        assertThat(result.contains(Number.from(3))).isTrue();
        assertThat(result.contains(Number.from(4))).isTrue();
        assertThat(result.contains(Number.from(5))).isTrue();
        assertThat(result.contains(Number.from(6))).isTrue();
    }

    @Test
    void 당첨로또를_반환한다() {
        //given
        InputHandlerStub inputHandlerStub = new InputHandlerStub();
        WinningLottoControllerImpl sut = new WinningLottoControllerImpl(inputHandlerStub);
        inputHandlerStub.stubBonusNumber(7);
        Lotto winningNumbers = Lotto.from(List.of(1, 2, 3, 4, 5, 6));

        //when
        WinningLotto result = sut.getWinningLotto(winningNumbers);

        //then
        assertThat(result.matchLotto(winningNumbers).isPresent()).isTrue();
        assertThat(result.matchLotto(winningNumbers).get()).isEqualTo(LottoPrize.FRIST_PRIZE);
    }
}
