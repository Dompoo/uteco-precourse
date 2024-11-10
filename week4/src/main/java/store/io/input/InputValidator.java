package store.io.input;

import java.util.List;
import java.util.regex.Pattern;
import store.common.exception.StoreExceptions;
import store.domain.validator.ParamsValidator;

public class InputValidator {

    private static final Pattern PURCHASE_PATTERN = Pattern.compile(
            "^\\s*\\[\\s*[a-zA-Z가-힣]+\\s*-\\s*[1-9]\\d*\\s*\\]\\s*$"
    );

    public void validateDecision(final String decisionInput) {
        ParamsValidator.validateParamsNotNull(decisionInput);
        validateLength(decisionInput);
        validateCharacter(decisionInput);
    }

    private static void validateLength(final String decisionInput) {
        if (decisionInput.length() != 1) {
            throw StoreExceptions.ILLEGAL_ARGUMENT.get();
        }
    }

    private static void validateCharacter(final String decisionInput) {
        char decision = decisionInput.charAt(0);
        if (decision != 'Y' && decision != 'N') {
            throw StoreExceptions.ILLEGAL_ARGUMENT.get();
        }
    }

    public void validatePurchases(final List<String> purchaseInputs) {
        ParamsValidator.validateParamsNotNull(purchaseInputs);
        purchaseInputs.forEach(this::validatePurchase);
    }

    private void validatePurchase(final String purchaseInput) {
        if (!PURCHASE_PATTERN.matcher(purchaseInput).matches()) {
            throw StoreExceptions.INVALID_PURCHASE_FORMAT.get();
        }
    }
}
