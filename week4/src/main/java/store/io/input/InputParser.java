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
        String[] split = input.substring(1, input.length() - 1).split("-");
        return new PurchaseRequest(
                split[0],
                Integer.parseInt(split[1])
        );
    }
}
