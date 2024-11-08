package store.domain;

import store.dto.request.PurchaseRequest;
import store.service.decisionService.DecisionSupplier;

final public class Casher {

    public static PurchaseType decidePurchaseType(
            Product product,
            PurchaseRequest purchaseRequest,
            DecisionType decisionType,
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
        return decideBringDefaultProductBack(product, purchaseRequest, bringDefaultProductBackSupplier);
    }

    private static PurchaseType decideBringFreeProduct(Product product, PurchaseRequest purchaseRequest,
                                                DecisionSupplier<Boolean> bringFreeProductPredicate) {
        if (bringFreeProductPredicate.get(
                product.getName(),
                calculatePromotionGetCount(product, purchaseRequest.count()))
        ) {
            return PurchaseType.FULL_PROMOTION_BRING_FREE;
        }
        return PurchaseType.FULL_PROMOTION_NOT_BRING_FREE;
    }

    private static int calculatePromotionGetCount(Product product, int purchaseAmount) {
        int promotionUnit = product.getPromotion().getPromotionUnit();
        return (((purchaseAmount / promotionUnit) + 1) * promotionUnit) - purchaseAmount;
    }

    private static PurchaseType decideBringDefaultProductBack(Product product, PurchaseRequest purchaseRequest,
                                                DecisionSupplier<Boolean> bringDefaultProductBackPredicate) {
        if (bringDefaultProductBackPredicate.get(
                product.getName(),
                calculateDefaultProductBackCount(product, purchaseRequest.count())
        )) {
            return PurchaseType.PORTION_PROMOTION_BRING_BACK;
        }
        return PurchaseType.PORTION_PROMOTION_NOT_BRING_BACK;
    }

    private static int calculateDefaultProductBackCount(Product product, int purchaseAmount) {
        int promotionUnit = product.getPromotion().getPromotionUnit();
        if (purchaseAmount < product.getPromotionStock()) {
            return purchaseAmount % promotionUnit;
        }
        return purchaseAmount - (product.getPromotionStock() / promotionUnit) * promotionUnit;
    }
}
