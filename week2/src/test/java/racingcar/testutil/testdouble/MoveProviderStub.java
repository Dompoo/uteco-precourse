package racingcar.testutil.testdouble;

import racingcar.domain.moveProvider.MoveProvider;

public class MoveProviderStub implements MoveProvider {

    @Override
    public boolean canMove() {
        return true;
    }
}
