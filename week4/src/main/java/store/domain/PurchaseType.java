package store.domain;

import store.domain.vo.PurchaseStatus;

public enum PurchaseType {

    FULL_DEFAULT(
            Purchase::getPurchaseAmount,
            (purchase) -> 0,
            (purchase) -> 0
    ),
    FULL_PROMOTION(
            Purchase::getPurchaseAmount,
            Purchase::getPromotionGetWithFullPromotion,
            Purchase::getPurchaseAmount
    ),
    FULL_PROMOTION_BRING_FREE(
            Purchase::getPurchaseAmountWithGetFree,
            Purchase::getPromotionGetWithGetFree,
            Purchase::getPurchaseAmountWithGetFree
    ),
    FULL_PROMOTION_NOT_BRING_FREE(
            Purchase::getPurchaseAmount,
            Purchase::getPromotionGetWithFullPromotion,
            Purchase::getPurchaseAmount
    ),
    PORTION_PROMOTION_BRING_BACK(
            Purchase::getPurchaseAmountWithBringBackNotPromotion,
            Purchase::getPromotionGetWithPortionPromotion,
            Purchase::getPurchaseAmountWithBringBackNotPromotion
    ),
    PORTION_PROMOTION_NOT_BRING_BACK(
            Purchase::getPurchaseAmount,
            Purchase::getPromotionGetWithPortionPromotion,
            Purchase::calculateStockLimitedPurchaseAmount
    ),
    ;

    private final StoreCalculator<Integer> finalPurchaseProductCountCalculator;
    private final StoreCalculator<Integer> promotionGetProductCountCalculator;
    private final StoreCalculator<Integer> promotionStockToDecreaseCalculator;

    PurchaseType(
            StoreCalculator<Integer> finalPurchaseProductCountCalculator,
            StoreCalculator<Integer> promotionGetProductCountCalculator,
            StoreCalculator<Integer> promotionStockToDecreaseCalculator
    ) {
        this.finalPurchaseProductCountCalculator = finalPurchaseProductCountCalculator;
        this.promotionGetProductCountCalculator = promotionGetProductCountCalculator;
        this.promotionStockToDecreaseCalculator = promotionStockToDecreaseCalculator;
    }

    public PurchaseStatus purchase(Purchase purchase) {
        Integer promotionGetProductCount = this.promotionGetProductCountCalculator.calculate(purchase);
        return new PurchaseStatus(
                this.finalPurchaseProductCountCalculator.calculate(purchase),
                promotionGetProductCount,
                this.promotionStockToDecreaseCalculator.calculate(purchase),
                purchase.calculatePromotionedProductAmount(promotionGetProductCount)
        );
    }

    @FunctionalInterface
    public interface StoreCalculator<T> {
        T calculate(Purchase purchase);
    }
}


