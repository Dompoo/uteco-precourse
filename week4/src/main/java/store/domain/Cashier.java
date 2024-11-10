package store.domain;

import store.service.decisionService.DecisionSupplier;

final public class Cashier {

    public static PurchaseType decidePurchaseType(
            Product product,
            int purchaseAmount,
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
            return decideBringFreeProduct(product, purchaseAmount, bringFreeProductSupplier);
        }
        return decideBringDefaultProductBack(product, purchaseAmount, bringDefaultProductBackSupplier);
    }

    private static PurchaseType decideBringFreeProduct(
            Product product, int purchaseAmount,
            DecisionSupplier<Boolean> bringFreeProductPredicate
    ) {
        if (bringFreeProductPredicate.get(product.getName(), product.calculatePromotionGets(purchaseAmount))) {
            return PurchaseType.FULL_PROMOTION_BRING_FREE;
        }
        return PurchaseType.FULL_PROMOTION_NOT_BRING_FREE;
    }

    private static PurchaseType decideBringDefaultProductBack(
            Product product,
            int purchaseAmount,
            DecisionSupplier<Boolean> bringDefaultProductBackPredicate
    ) {
        if (bringDefaultProductBackPredicate.get(product.getName(), product.calculateNoPromotions(purchaseAmount))) {
            return PurchaseType.PORTION_PROMOTION_NOT_BRING_BACK;
        }
        return PurchaseType.PORTION_PROMOTION_BRING_BACK;
    }
}
