package store.service.purchaseService;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Supplier;
import store.domain.PurchaseType;
import store.dto.request.PurchaseRequest;
import store.dto.response.PurchaseResult;

public interface PurchaseService {

    List<PurchaseRequest> getPurchases(Supplier<List<PurchaseRequest>> purchaseRequestsSupplier);

    PurchaseResult purchaseProduct(PurchaseRequest purchaseRequest, PurchaseType purchaseType, LocalDate localDate);
}
