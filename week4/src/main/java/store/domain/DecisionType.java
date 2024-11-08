package store.domain;

import store.exception.StoreExceptions;

public enum DecisionType {

    FULL_DEFAULT,
    FULL_PROMOTION,
    CAN_GET_FREE_PRODUCT,
    PROMOTION_STOCK_LACK,
    ;

    public static DecisionType of(
            int purchaseAmount,
            int defaultStock,
            int promotionStock,
            int promotionBuy,
            int promotionGet,
            boolean canPromotion
    ) {
        validateStockSufficient(purchaseAmount, defaultStock, promotionStock);
        if (!canPromotion || promotionStock == 0) {
            return FULL_DEFAULT;
        }
        if (isJustRightPromotionUnit(purchaseAmount, promotionStock, promotionBuy, promotionGet)) {
            return FULL_PROMOTION;
        }
        if (canGetFreePromotion(purchaseAmount, promotionStock, promotionBuy, promotionGet)) {
            return CAN_GET_FREE_PRODUCT;
        }
        return PROMOTION_STOCK_LACK;
    }

    private static boolean isJustRightPromotionUnit(
            int purchaseAmount,
            int promotionStock,
            int promotionBuy,
            int promotionGet
    ) {
        return purchaseAmount % (promotionBuy + promotionGet) == 0 && purchaseAmount <= promotionStock;
    }

    private static boolean canGetFreePromotion(
            int purchaseAmount,
            int promotionStock,
            int promotionBuy,
            int promotionGet
    ) {
        return purchaseAmount % (promotionBuy + promotionGet) >= promotionBuy
                && purchaseAmount + promotionGet <= promotionStock;
    }

    private static void validateStockSufficient(int purchaseAmount, int defaultStock, int promotionStock) {
        if (purchaseAmount > defaultStock + promotionStock) {
            throw StoreExceptions.PURCHASE_OVER_STOCK.get();
        }
    }
}
