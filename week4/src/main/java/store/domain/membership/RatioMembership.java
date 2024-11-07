package store.domain.membership;

final public class RatioMembership implements Membership {

    private static final double MEMBERSHIP_SALE_RATIO = 0.3;
    private static final int MAX_MEMBERSHIP_SALE_AMOUNT = 8000;

    @Override
    public int calculateMembershipSaleAmount(int originalPurchaseCost, int promotionSaleCost) {
        int saleAmount = (int) ((originalPurchaseCost - promotionSaleCost) * MEMBERSHIP_SALE_RATIO);

        return Math.min(saleAmount, MAX_MEMBERSHIP_SALE_AMOUNT);
    }
}
