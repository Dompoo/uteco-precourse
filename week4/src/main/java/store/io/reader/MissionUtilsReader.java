package store.io.reader;

import camp.nextstep.edu.missionutils.Console;
import java.util.function.Supplier;

public class MissionUtilsReader implements Reader {

    @Override
    public String[] readLineAsStrings(String spliter) {
        return withHandleException(() -> {
            String input = Console.readLine();
            return input.split(spliter);
        });
    }

    @Override
    public String readLineAsString() {
        return withHandleException(Console::readLine);
    }

    private static <T> T withHandleException(Supplier<T> supplier) {
        try {
            return supplier.get();
        } catch (IllegalArgumentException illegalArgumentException) {
            throw new IllegalArgumentException("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.");
        }
    }
}
