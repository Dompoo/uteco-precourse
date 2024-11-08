package store.io.input;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import store.dto.request.PurchaseRequest;

public class InputParser {

    private static final Pattern PURCHASE_PATTERN = Pattern.compile("\\[\\w+-\\d+\\]");

    public boolean parseYesOrNo(String input) {
        return input.equals("Y");
    }

    public List<PurchaseRequest> parsePurchases(List<String> input) {
        List<PurchaseRequest> purchaseRequests = new ArrayList<>();
        for (String purchase : input) {
            Matcher matcher = PURCHASE_PATTERN.matcher(purchase);
            if (matcher.matches()) {
                purchaseRequests.add(new PurchaseRequest(
                        matcher.group(1),
                        Integer.parseInt(matcher.group(2))
                ));
            }
        }
        return purchaseRequests;
    }
}
