package store.io.reader;

import camp.nextstep.edu.missionutils.Console;
import store.exception.StoreExceptions;

public class MissionUtilsReader implements Reader {

    @Override
    public String[] readLineAsStrings(String spliter) {
        try {
            String input = Console.readLine();
            return input.split(spliter);
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
