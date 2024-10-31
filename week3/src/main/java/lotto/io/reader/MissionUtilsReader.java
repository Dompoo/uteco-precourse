package lotto.io.reader;

import camp.nextstep.edu.missionutils.Console;
import java.util.Arrays;
import java.util.List;
import lotto.exception.IllegalInputException;
import lotto.exception.IllegalNumberFormatException;

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
            throw new IllegalNumberFormatException();
        } catch (Exception exception) {
            throw new IllegalInputException();
        }
    }

    @Override
    public int readLineAsNumber() {
        try {
            String input = Console.readLine();
            return Integer.parseInt(input);
        } catch (NumberFormatException numberFormatException) {
            throw new IllegalNumberFormatException();
        } catch (Exception exception) {
            throw new IllegalInputException();
        }
    }
}
