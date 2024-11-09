package store.io.input;

import java.util.ArrayList;
import java.util.List;
import store.dto.request.PurchaseRequest;

public class InputParser {

    public boolean parseYesOrNo(String input) {
        return input.equals("Y");
    }

    public List<PurchaseRequest> parsePurchases(List<String> inputs) {
        List<PurchaseRequest> purchaseRequests = new ArrayList<>();
        for (String input : inputs) {
            purchaseRequests.add(parseToParchaseRequest(input));
        }
        return purchaseRequests;
    }

    private static PurchaseRequest parseToParchaseRequest(String input) {
        String withOuputBlank = input.replaceAll("\\s+", "");
        String[] inputs = withOuputBlank.substring(1, withOuputBlank.length() - 1)
                .split("-");
        return new PurchaseRequest(inputs[0], Integer.parseInt(inputs[1])
        );
    }
}
