package store.io.reader;

import camp.nextstep.edu.missionutils.Console;
import java.util.Arrays;
import java.util.List;
import store.exception.StoreExceptions;

public class MissionUtilsReader implements Reader {

    @Override
    public List<String> readLineAsStrings(String spliter) {
        try {
            String input = Console.readLine();
            return Arrays.asList(input.split(spliter));
        } catch (IllegalArgumentException illegalArgumentException) {
            throw StoreExceptions.INVALID_PURCHASE_FORMAT.get();
        }
    }

    @Override
    public String readLineAsString() {
        try {
            return Console.readLine();
        } catch (IllegalArgumentException illegalArgumentException) {
            throw StoreExceptions.ILLEGAL_ARGUMENT.get();
        }
    }
}
