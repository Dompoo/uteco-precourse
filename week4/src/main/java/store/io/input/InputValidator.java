package store.io.input;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import store.common.exception.StoreExceptions;

public class InputValidator {

    private static final Pattern DECISION_PATTERN = Pattern.compile("^\\s*[YN]\\s*$");
    private static final Pattern PURCHASE_PATTERN = Pattern.compile("^\\s*\\[\\s*(\\D+)\\s*-\\s*(\\d+)\\s*\\]\\s*$");

    public void validateDecision(String decisionInput) {
        Matcher matcher = DECISION_PATTERN.matcher(decisionInput);
        if (!matcher.matches()) {
            throw StoreExceptions.ILLEGAL_ARGUMENT.get();
        }
    }

    public void validatePurchases(List<String> purchaseInputs) {
        purchaseInputs.stream()
                .map(PURCHASE_PATTERN::matcher)
                .forEach(matcher -> {
                    if (!matcher.matches()) {
                        throw StoreExceptions.INVALID_PURCHASE_FORMAT.get();
                    }
                });
    }
}
