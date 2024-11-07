package store.domain.membership;

public interface Membership {

    int calculateMembershipSaleAmount(int originalPurchaseCost, int promotionSaleCost);
}
