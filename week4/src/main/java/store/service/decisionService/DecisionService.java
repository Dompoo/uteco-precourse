package store.service.decisionService;

import java.time.LocalDate;
import java.util.function.Supplier;
import store.domain.DecisionType;
import store.domain.PurchaseType;
import store.domain.membership.Membership;
import store.dto.request.PurchaseRequest;

public interface DecisionService {

    DecisionType getDecisionType(PurchaseRequest purchaseRequest, LocalDate localDate);

    PurchaseType decidePurchaseType(
            PurchaseRequest purchaseRequest,
            DecisionType decisionType,
            DecisionSupplier<Boolean> bringFreeProductDecisionSupplier,
            DecisionSupplier<Boolean> bringDefaultProductBackDecisionSupplier
    );

    Membership decideMembership(
            Supplier<Boolean> membershipDecisionSupplier
    );
}
