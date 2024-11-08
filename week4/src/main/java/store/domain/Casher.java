package store.domain;

import java.util.function.BiPredicate;
import store.dto.request.PurchaseRequest;

final public class Casher {

    public static PurchaseType decidePurchaseType(
            Product product,
            PurchaseRequest purchaseRequest,
            DecisionType decisionType,
            BiPredicate<String, Integer> bringFreeProductPredicate,
            BiPredicate<String, Integer> bringDefaultProductBackPredicate
    ) {
        if (decisionType == DecisionType.FULL_DEFAULT) {
            return PurchaseType.FULL_DEFAULT;
        }
        if (decisionType == DecisionType.FULL_PROMOTION) {
            return PurchaseType.FULL_PROMOTION;
        }
        if (decisionType == DecisionType.CAN_GET_FREE_PRODUCT) {
            return decideBringFreeProduct(product, purchaseRequest, bringFreeProductPredicate);
        }
        return decideBringDefaultProductBack(product, purchaseRequest, bringDefaultProductBackPredicate);
    }

    private static PurchaseType decideBringFreeProduct(Product product, PurchaseRequest purchaseRequest,
                                                BiPredicate<String, Integer> bringFreeProductPredicate) {
        if (bringFreeProductPredicate.test(
                product.getName(),
                calculatePromotionGetCount(product, purchaseRequest.count()))
        ) {
            return PurchaseType.FULL_PROMOTION_BRING_FREE;
        }
        return PurchaseType.FULL_PROMOTION_NOT_BRING_FREE;
    }

    private static int calculatePromotionGetCount(Product product, int purchaseAmount) {
        int promotionUnit = product.getPromotion().getBuy() + product.getPromotion().getGet();

        return (((purchaseAmount / promotionUnit) + 1) * promotionUnit) - purchaseAmount;
    }

    private static PurchaseType decideBringDefaultProductBack(Product product, PurchaseRequest purchaseRequest,
                                                BiPredicate<String, Integer> bringDefaultProductBackPredicate) {
        if (bringDefaultProductBackPredicate.test(
                product.getName(),
                calculateDefaultProductBackCount(product, purchaseRequest.count())
        )) {
            return PurchaseType.PORTION_PROMOTION_BRING_BACK;
        }
        return PurchaseType.PORTION_PROMOTION_NOT_BRING_BACK;
    }

    private static int calculateDefaultProductBackCount(Product product, int purchaseAmount) {
        int promotionUnit = product.getPromotion().getBuy() + product.getPromotion().getGet();
        if (purchaseAmount < product.getPromotionStock()) {
            return purchaseAmount % promotionUnit;
        }
        return purchaseAmount - (product.getPromotionStock() / promotionUnit) * promotionUnit;
    }
}
