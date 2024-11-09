package store.domain;

import java.time.LocalDate;
import store.dto.request.PurchaseRequest;
import store.service.decisionService.DecisionSupplier;

final public class Casher {

    public static PurchaseType decidePurchaseType(
            Product product,
            PurchaseRequest purchaseRequest,
            DecisionType decisionType,
            LocalDate localDate,
            DecisionSupplier<Boolean> bringFreeProductSupplier,
            DecisionSupplier<Boolean> bringDefaultProductBackSupplier
    ) {
        if (decisionType == DecisionType.FULL_DEFAULT) {
            return PurchaseType.FULL_DEFAULT;
        }
        if (decisionType == DecisionType.FULL_PROMOTION) {
            return PurchaseType.FULL_PROMOTION;
        }
        if (decisionType == DecisionType.CAN_GET_FREE_PRODUCT) {
            return decideBringFreeProduct(product, purchaseRequest, bringFreeProductSupplier);
        }
        return decideBringDefaultProductBack(product, purchaseRequest, bringDefaultProductBackSupplier, localDate);
    }

    private static PurchaseType decideBringFreeProduct(Product product, PurchaseRequest purchaseRequest,
                                                DecisionSupplier<Boolean> bringFreeProductPredicate) {
        if (bringFreeProductPredicate.get(
                product.getName(),
                product.calculatePromotionGets(purchaseRequest.purchaseAmount()))
        ) {
            return PurchaseType.FULL_PROMOTION_BRING_FREE;
        }
        return PurchaseType.FULL_PROMOTION_NOT_BRING_FREE;
    }

    private static PurchaseType decideBringDefaultProductBack(
            Product product,
            PurchaseRequest purchaseRequest,
            DecisionSupplier<Boolean> bringDefaultProductBackPredicate,
            LocalDate localDate
    ) {
        if (bringDefaultProductBackPredicate.get(
                product.getName(),
                product.calculateNoPromotions(purchaseRequest.purchaseAmount(), localDate)
        )) {
            return PurchaseType.PORTION_PROMOTION_NOT_BRING_BACK;
        }
        return PurchaseType.PORTION_PROMOTION_BRING_BACK;
    }
}
