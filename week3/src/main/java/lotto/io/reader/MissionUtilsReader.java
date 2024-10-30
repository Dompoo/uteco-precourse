package lotto.io.reader;

import camp.nextstep.edu.missionutils.Console;
import java.util.Arrays;
import java.util.List;
import lotto.constants.ExceptionMessages;

public class MissionUtilsReader implements Reader {

    @Override
    public List<Integer> readLineAsNumbers(String spliter) {
        try {
            String input = Console.readLine();
            return Arrays.stream(input.split(spliter))
                    .map(String::trim)
                    .map(Integer::parseInt)
                    .toList();
        } catch (NumberFormatException numberFormatException) {
            throw new IllegalArgumentException(ExceptionMessages.ILLEGAL_NUMBER_FORMAT.message, numberFormatException);
        } catch (Exception exception) {
            throw new IllegalArgumentException(ExceptionMessages.ILLEGAL_INPUT.message, exception);
        }
    }

    @Override
    public int readLineAsNumber() {
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
