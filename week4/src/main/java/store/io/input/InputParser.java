package store.io.input;

import java.util.List;
import store.dto.request.PurchaseRequest;

public class InputParser {

    public boolean parseDecision(String decisionInput) {
        return decisionInput.replaceAll("\\s+", "").equals("Y");
    }

    public List<PurchaseRequest> parsePurchases(List<String> purchaseInputs, String purchaseAmountSeparator) {
        return purchaseInputs.stream()
                .map(input -> parseToParchaseRequest(input, purchaseAmountSeparator))
                .toList();
    }

    private static PurchaseRequest parseToParchaseRequest(String purchaseInputs, String purchaseAmountSeparator) {
        String[] inputs = purchaseInputs
                .replaceAll("\\s+", "")
                .replaceAll("[\\[\\]]", "")
                .split(purchaseAmountSeparator);
        return new PurchaseRequest(inputs[0], Integer.parseInt(inputs[1]));
    }
}
