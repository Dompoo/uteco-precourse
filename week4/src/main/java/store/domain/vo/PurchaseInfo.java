package store.domain.vo;

public record PurchaseInfo(
        int purchaseAmount,
        int promotionStock,
        int promotionBuy,
        int promotionGet
) {
}