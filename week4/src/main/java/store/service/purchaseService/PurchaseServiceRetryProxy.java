package store.service.purchaseService;

import java.util.List;
import java.util.function.Supplier;
import store.aop.RetryHandler;
import store.domain.PurchaseType;
import store.dto.request.PurchaseRequest;
import store.dto.response.PurchaseResult;

public class PurchaseServiceRetryProxy implements PurchaseService {

    private final PurchaseService purchaseServiceTarget;
    private final RetryHandler retryHandler;

    public PurchaseServiceRetryProxy(PurchaseService purchaseServiceTarget, RetryHandler retryHandler) {
        this.purchaseServiceTarget = purchaseServiceTarget;
        this.retryHandler = retryHandler;
    }

    @Override
    public List<PurchaseRequest> getPurchases(Supplier<List<PurchaseRequest>> purchaseRequestsSupplier) {
        return retryHandler.tryUntilSuccess(() -> purchaseServiceTarget.getPurchases(purchaseRequestsSupplier));
    }

    @Override
    public PurchaseResult purchaseProduct(PurchaseRequest purchaseRequest, PurchaseType purchaseType) {
        return retryHandler.tryUntilSuccess(() -> purchaseServiceTarget.purchaseProduct(purchaseRequest, purchaseType));
    }
}
