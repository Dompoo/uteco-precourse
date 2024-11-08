package store.dto.response;

public record PurchaseResult(
        String productName,
        int purchaseAmount,
        int promotionedProductAmount,
        int price,
        int promotionGetAmount
) {
}
