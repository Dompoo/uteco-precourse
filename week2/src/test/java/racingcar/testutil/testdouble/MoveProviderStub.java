package racingcar.testutil.testdouble;

import racingcar.moveProvider.MoveProvider;

public class MoveProviderStub implements MoveProvider {

    @Override
    public boolean canMove() {
        return true;
    }
}
