package store.domain.membership;

import java.util.List;
import store.dto.response.PurchaseResult;

public interface Membership {

    int calculateMembershipSaleAmount(List<PurchaseResult> purchaseResults);
}
