package store.service.purchaseService;

import java.util.List;
import java.util.function.Supplier;
import store.common.dto.request.PurchaseRequest;
import store.common.dto.response.PurchaseResult;
import store.domain.PurchaseType;

public interface PurchaseService {

    List<PurchaseRequest> getPurchases(Supplier<List<PurchaseRequest>> purchaseRequestsSupplier);

    PurchaseResult purchaseProduct(PurchaseRequest purchaseRequest, PurchaseType purchaseType);
}
