package store.io.input;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputValidator {

    private static final List<String> VALID_YES_OR_NO = List.of("Y", "N");
    private static final Pattern PURCHASE_PATTERN = Pattern.compile("\\[(\\D+)-(\\d+)\\]");

    public void validateYOrN(String input) {
        if (!VALID_YES_OR_NO.contains(input)) {
            throw new IllegalArgumentException("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.");
        }
    }

    public void validatePurchases(List<String> purchases) {
        for (String purchase : purchases) {
            Matcher matcher = PURCHASE_PATTERN.matcher(purchase);
            if (!matcher.find()) {
                throw new IllegalArgumentException("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.");
            }
        }
    }
}
