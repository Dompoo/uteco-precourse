package store.domain.membership;

import java.util.List;
import store.dto.response.PurchaseResult;

final public class NoMembership implements Membership {

    @Override
    public int calculateMembershipSaleAmount(List<PurchaseResult> purchaseResults) {
        return 0;
    }
}
