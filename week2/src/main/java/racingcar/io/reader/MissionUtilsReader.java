package racingcar.io.reader;

import camp.nextstep.edu.missionutils.Console;

public class MissionUtilsReader implements Reader {

    @Override
    public String readLine() {
        try {
            return Console.readLine();
        } catch (Exception e) {
            throw new IllegalArgumentException("잘못된 입력입니다.", e);
        }
    }

    @Override
    public int readLineToInt() {
        try {
            String input = Console.readLine();
            return Integer.parseInt(input);
        } catch (Exception e) {
            throw new IllegalArgumentException("잘못된 입력입니다.", e);
        }
    }
}
