package store.domain.membership;

import java.util.List;
import store.common.dto.response.PurchaseResult;

final public class RatioMembership implements Membership {

    private static final double MEMBERSHIP_SALE_RATIO = 0.3;
    private static final int MAX_MEMBERSHIP_SALE_AMOUNT = 8000;

    @Override
    public int calculateMembershipSaleAmount(List<PurchaseResult> purchaseResults) {
        int totalDefaultPurchasePrice = purchaseResults.stream()
                .mapToInt(PurchaseResult::getDefaultPurchasePrice)
                .sum();
        return (int) Math.min(totalDefaultPurchasePrice * MEMBERSHIP_SALE_RATIO, MAX_MEMBERSHIP_SALE_AMOUNT);
    }
}
