package lotto.testUtil.testDouble;

import java.util.ArrayList;
import java.util.List;
import lotto.domain.numberPicker.NumberPicker;

public class NumberPickerFake implements NumberPicker {

    private final List<Integer> numbers = new ArrayList<>();

    public void setNumbers(Integer... numbers) {
        this.numbers.addAll(List.of(numbers));
    }

    @Override
    public List<Integer> pickUniqueNumbersInRange(int startInclusive, int endInclusive, int count) {
        return numbers.subList(0, count);
    }
}
