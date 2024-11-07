package store.domain;

public enum DecisionType {

    FULL_DEFAULT,
    FULL_PROMOTION,
    CAN_GET_FREE_PRODUCT,
    PROMOTION_STOCK_LACK,
    ;

    public static DecisionType getDecisionType(
            int purchaseAmount,
            int defaultStock,
            int promotionStock,
            int promotionBuy,
            int promotionGet
    ) {
        validateStockSufficient(purchaseAmount, defaultStock, promotionStock);
        if (promotionStock == 0) {
            return FULL_DEFAULT;
        }
        if (purchaseAmount % (promotionBuy + promotionGet) == 0
                && purchaseAmount <= promotionStock) {
            return FULL_PROMOTION;
        }
        if (purchaseAmount % (promotionBuy + promotionGet) == promotionBuy
                && purchaseAmount + promotionGet <= promotionStock) {
            return CAN_GET_FREE_PRODUCT;
        }
        return PROMOTION_STOCK_LACK;
    }

    private static void validateStockSufficient(int purchaseAmount, int defaultStock, int promotionStock) {
        if (purchaseAmount > defaultStock + promotionStock) {
            throw new RuntimeException("재고부족");
        }
    }
}
