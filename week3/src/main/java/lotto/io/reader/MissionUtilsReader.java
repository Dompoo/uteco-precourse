package lotto.io.reader;

import camp.nextstep.edu.missionutils.Console;

public class MissionUtilsReader implements Reader {

    @Override
    public String readLine() {
        try {
            return Console.readLine();
        } catch (Exception exception) {
            throw new IllegalArgumentException("잘못된 입력입니다.", exception);
        }
    }

    @Override
    public int readLineAsInt() {
        try {
            String input = Console.readLine();
            return Integer.parseInt(input);
        } catch (NumberFormatException numberFormatException) {
            throw new IllegalArgumentException("잘못된 숫자 형식입니다.", numberFormatException);
        } catch (Exception exception) {
            throw new IllegalArgumentException("잘못된 입력입니다.", exception);
        }
    }
}
