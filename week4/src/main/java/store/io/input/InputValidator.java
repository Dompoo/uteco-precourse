package store.io.input;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import store.exception.StoreExceptions;

public class InputValidator {

    private static final List<String> VALID_YES_OR_NO = List.of("Y", "N");
    private static final Pattern PURCHASE_PATTERN = Pattern.compile("\\[\\s*(\\D+)\\s*-\\s*(\\d+)\\s*\\]");

    public void validateYOrN(String input) {
        if (!VALID_YES_OR_NO.contains(input)) {
            throw StoreExceptions.ILLEGAL_ARGUMENT.get();
        }
    }

    public void validatePurchases(List<String> purchases) {
        for (String purchase : purchases) {
            Matcher matcher = PURCHASE_PATTERN.matcher(purchase);
            if (!matcher.find()) {
                throw StoreExceptions.INVALID_PURCHASE_FORMAT.get();
            }
        }
    }
}
