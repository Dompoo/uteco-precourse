package store.io.input;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import store.exception.StoreExceptions;

public class InputValidator {

    private static final Pattern YES_OR_NO_PATTERN = Pattern.compile("^\\s*[YN]\\s*$");
    private static final Pattern PURCHASE_PATTERN = Pattern.compile("^\\s*\\[\\s*(\\D+)\\s*-\\s*(\\d+)\\s*\\]\\s*$");

    public void validateYOrN(String input) {
        Matcher matcher = YES_OR_NO_PATTERN.matcher(input);
        if (!matcher.matches()) {
            throw StoreExceptions.ILLEGAL_ARGUMENT.get();
        }
    }

    public void validatePurchases(List<String> purchases) {
        for (String purchase : purchases) {
            Matcher matcher = PURCHASE_PATTERN.matcher(purchase);
            if (!matcher.matches()) {
                throw StoreExceptions.INVALID_PURCHASE_FORMAT.get();
            }
        }
    }
}
