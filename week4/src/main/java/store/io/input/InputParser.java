package store.io.input;

import java.util.List;
import store.common.dto.request.PurchaseRequest;

public class InputParser {

    public boolean parseDecision(final String decisionInput) {
        return decisionInput.replaceAll("\\s+", "").equals("Y");
    }

    public List<PurchaseRequest> parsePurchases(
            final List<String> purchaseInputs,
            final String purchaseAmountSeparator
    ) {
        return purchaseInputs.stream()
                .map(input -> parseToParchaseRequest(input, purchaseAmountSeparator))
                .toList();
    }

    private static PurchaseRequest parseToParchaseRequest(
            final String purchaseInputs,
            final String purchaseAmountSeparator
    ) {
        String[] inputs = purchaseInputs
                .replaceAll("\\s+", "")
                .replaceAll("[\\[\\]]", "")
                .split(purchaseAmountSeparator);
        return new PurchaseRequest(inputs[0], Integer.parseInt(inputs[1]));
    }
}
