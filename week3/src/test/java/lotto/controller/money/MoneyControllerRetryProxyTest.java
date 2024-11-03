package lotto.controller.money;

import static org.assertj.core.api.Assertions.assertThat;

import lotto.domain.Money;
import lotto.testUtil.testDouble.InputHandlerStub;
import org.junit.jupiter.api.Test;

class MoneyControllerRetryProxyTest {

    @Test
    void 돈을_반환한다() {
        //given
        InputHandlerStub inputHandlerStub = new InputHandlerStub();
        MoneyControllerImpl sut = new MoneyControllerImpl(inputHandlerStub);
        inputHandlerStub.stubPurchaseCost(10000);

        //when
        Money result = sut.getMoney();

        //then
        assertThat(result.getAmount()).isEqualTo(10000);
    }
}
