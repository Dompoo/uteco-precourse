package lotto.io.reader;

import camp.nextstep.edu.missionutils.Console;
import lotto.constants.ExceptionMessages;

public class MissionUtilsReader implements Reader {

    @Override
    public String readLine() {
        try {
            return Console.readLine();
        } catch (Exception exception) {
            throw new IllegalArgumentException(ExceptionMessages.ILLEGAL_INPUT.message, exception);
        }
    }

    @Override
    public int readLineAsInt() {
        try {
            String input = Console.readLine();
            return Integer.parseInt(input);
        } catch (NumberFormatException numberFormatException) {
            throw new IllegalArgumentException(ExceptionMessages.ILLEGAL_NUMBER_FORMAT.message, numberFormatException);
        } catch (Exception exception) {
            throw new IllegalArgumentException(ExceptionMessages.ILLEGAL_INPUT.message, exception);
        }
    }
}
