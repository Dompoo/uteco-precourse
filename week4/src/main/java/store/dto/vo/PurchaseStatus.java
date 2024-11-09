package store.dto.vo;

public record PurchaseStatus(
        int finalPurchaseAmount,
        int promotionGetAmount,
        int decreasePromotionStock,
        int promotionedProductAmount
) {
}