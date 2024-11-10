package store.domain.membership;

import java.util.List;
import store.common.dto.response.PurchaseResult;

public interface Membership {

    int calculateMembershipSaleAmount(List<PurchaseResult> purchaseResults);
}
