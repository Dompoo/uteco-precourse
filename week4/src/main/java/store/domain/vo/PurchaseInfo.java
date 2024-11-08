package store.domain.vo;

public record PurchaseInfo(
        int purchaseAmount,
        int promotionStock,
        int promotionBuy,
        int promotionGet
) {

    public int promotionUnit() {
        return this.promotionBuy + this.promotionGet;
    }
}