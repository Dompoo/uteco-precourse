package store.domain;

import store.exception.StoreExceptions;

final public class Purchase {

    private final int purchaseAmount;
    private final int promotionStock;
    private final int promotionBuy;
    private final int promotionGet;

    public Purchase(int purchaseAmount, int promotionStock, int promotionBuy, int promotionGet) {
        validate(purchaseAmount, promotionStock, promotionBuy, promotionGet);
        this.purchaseAmount = purchaseAmount;
        this.promotionStock = promotionStock;
        this.promotionBuy = promotionBuy;
        this.promotionGet = promotionGet;
    }

    private void validate(int purchaseAmount, int promotionStock, int promotionBuy, int promotionGet) {
        if (purchaseAmount < 0) {
            throw StoreExceptions.ILLEGAL_ARGUMENT.get();
        }
        if (promotionStock < 0) {
            throw StoreExceptions.ILLEGAL_ARGUMENT.get();
        }
        if (promotionBuy < 1) {
            throw StoreExceptions.ILLEGAL_ARGUMENT.get();
        }
        if (promotionGet < 1) {
            throw StoreExceptions.ILLEGAL_ARGUMENT.get();
        }
    }

    public int getPurchaseAmount() {
        return purchaseAmount;
    }

    public int calculatePromotionUnit() {
        return this.promotionBuy + this.promotionGet;
    }

    public int getPurchaseAmountWithGetFree() {
        return (this.purchaseAmount / calculatePromotionUnit() + 1) * calculatePromotionUnit();
    }

    public int getPurchaseAmountWithBringBackNotPromotion() {
        return (calculateStockLimitedPurchaseAmount() / calculatePromotionUnit()) * calculatePromotionUnit();
    }

    public int calculateStockLimitedPurchaseAmount() {
        return Math.min(this.purchaseAmount, this.promotionStock);
    }

    public int getPromotionGetWithFullPromotion() {
        return (this.purchaseAmount / calculatePromotionUnit()) * this.promotionGet;
    }

    public int getPromotionGetWithGetFree() {
        return ((this.purchaseAmount / calculatePromotionUnit()) + 1) * this.promotionGet;
    }

    public int getPromotionGetWithPortionPromotion() {
        return (calculateStockLimitedPurchaseAmount() / calculatePromotionUnit()) * this.promotionGet;
    }

    public int calculatePromotionedProductAmount(int promotionGetProductCount) {
        return promotionGetProductCount / this.promotionGet * calculatePromotionUnit();
    }
}
