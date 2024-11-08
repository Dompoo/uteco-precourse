package store.domain.membership;

import java.util.List;
import store.dto.response.PurchaseResult;

final public class RatioMembership implements Membership {

    private static final double MEMBERSHIP_SALE_RATIO = 0.3;
    private static final int MAX_MEMBERSHIP_SALE_AMOUNT = 8000;

    @Override
    public int calculateMembershipSaleAmount(List<PurchaseResult> purchaseResults) {
        int totalDefaultPurchasePrice = 0;
        for (PurchaseResult purchaseResult : purchaseResults) {
            int defaultPurchaseAmount = purchaseResult.purchaseAmount() - purchaseResult.promotionedProductAmount();
            totalDefaultPurchasePrice += defaultPurchaseAmount * purchaseResult.price();
        }
        return (int) Math.min(totalDefaultPurchasePrice * MEMBERSHIP_SALE_RATIO, MAX_MEMBERSHIP_SALE_AMOUNT);
    }
}
