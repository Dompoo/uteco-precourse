package store.domain.membership;

final public class NoMembership implements Membership {

    @Override
    public int calculateMembershipSaleAmount(int originalPurchaseCost, int promotionSaleCost) {
        return 0;
    }
}
