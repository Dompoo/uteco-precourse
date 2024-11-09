package store.domain;

import java.time.LocalDate;
import store.exception.StoreExceptions;

final public class Purchase {

    private final int purchaseAmount;
    private final int promotionStock;
    private final int promotionBuy;
    private final int promotionGet;

    private Purchase(int purchaseAmount, int promotionStock, int promotionBuy, int promotionGet) {
        validate(purchaseAmount, promotionStock);
        this.purchaseAmount = purchaseAmount;
        this.promotionStock = promotionStock;
        this.promotionBuy = promotionBuy;
        this.promotionGet = promotionGet;
    }

    public static Purchase of(Product product, int purchaseAmount, LocalDate localDate) {
        if (product.hasPromotion(localDate)) {
            return new Purchase(purchaseAmount, product.getPromotionStock(localDate), product.getPromotion().getBuy(),
                    product.getPromotion().getGet());
        }
        return new Purchase(purchaseAmount, product.getPromotionStock(localDate), 0, 0);
    }

    private void validate(int purchaseAmount, int promotionStock) {
        if (purchaseAmount < 0) {
            throw StoreExceptions.ILLEGAL_ARGUMENT.get();
        }
        if (promotionStock < 0) {
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
        if (this.promotionGet == 0) {
            return 0;
        }
        return promotionGetProductCount / this.promotionGet * calculatePromotionUnit();
    }
}
