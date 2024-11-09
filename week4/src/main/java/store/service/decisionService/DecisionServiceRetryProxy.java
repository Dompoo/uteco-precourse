package store.service.decisionService;

import java.util.function.Supplier;
import store.aop.RetryHandler;
import store.domain.DecisionType;
import store.domain.PurchaseType;
import store.domain.membership.Membership;
import store.dto.request.PurchaseRequest;

public class DecisionServiceRetryProxy implements DecisionService {

    private final DecisionService decisionServiceTarget;
    private final RetryHandler retryHandler;

    public DecisionServiceRetryProxy(DecisionService decisionServiceTarget, RetryHandler retryHandler) {
        this.decisionServiceTarget = decisionServiceTarget;
        this.retryHandler = retryHandler;
    }

    @Override
    public DecisionType getDecisionType(PurchaseRequest purchaseRequest) {
        return retryHandler.tryUntilSuccess(() -> decisionServiceTarget.getDecisionType(purchaseRequest));
    }

    @Override
    public PurchaseType decidePurchaseType(
            PurchaseRequest purchaseRequest,
            DecisionType decisionType,
            DecisionSupplier<Boolean> bringFreeProductDecisionSupplier,
            DecisionSupplier<Boolean> bringDefaultProductBackDecisionSupplier
    ) {
        return retryHandler.tryUntilSuccess(() -> decisionServiceTarget.decidePurchaseType(
                purchaseRequest,
                decisionType,
                bringFreeProductDecisionSupplier,
                bringDefaultProductBackDecisionSupplier
        ));
    }

    @Override
    public Membership decideMembership(Supplier<Boolean> membershipDecisionSupplier) {
        return retryHandler.tryUntilSuccess(() -> decisionServiceTarget.decideMembership(membershipDecisionSupplier));
    }
}
