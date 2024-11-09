package store.domain;

import java.time.LocalDate;
import store.exception.StoreExceptions;

public enum DecisionType {

    FULL_DEFAULT,
    FULL_PROMOTION,
    CAN_GET_FREE_PRODUCT,
    PROMOTION_STOCK_LACK,
    ;

    public static DecisionType of(Product product, int purchaseAmount, LocalDate localDate) {
        validateStockSufficient(product, purchaseAmount);
        if (!product.hasPromotion(localDate) || product.getPromotionStock(localDate) == 0) {
            return FULL_DEFAULT;
        }
        if (product.isJustRightPromotionUnit(purchaseAmount, localDate)) {
            return FULL_PROMOTION;
        }
        if (product.canGetFreePromotionProduct(purchaseAmount, localDate)) {
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
