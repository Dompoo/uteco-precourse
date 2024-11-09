package store.domain;

import store.exception.StoreExceptions;

public enum DecisionType {

    FULL_DEFAULT,
    FULL_PROMOTION,
    CAN_GET_FREE_PRODUCT,
    PROMOTION_STOCK_LACK,
    ;

    public static DecisionType of(Product product, int purchaseAmount) {
        validateStockSufficient(product, purchaseAmount);
        if (!product.hasPromotion() || product.getPromotionStock() == 0) {
            return FULL_DEFAULT;
        }
        if (product.isJustRightPromotionUnit(purchaseAmount)) {
            return FULL_PROMOTION;
        }
        if (product.canGetFreePromotionProduct(purchaseAmount)) {
            return CAN_GET_FREE_PRODUCT;
        }
        return PROMOTION_STOCK_LACK;
    }

    private static void validateStockSufficient(Product product, int purchaseAmount) {
        if (!product.canPurchase(purchaseAmount)) {
            throw StoreExceptions.PURCHASE_OVER_STOCK.get();
        }
    }
}
