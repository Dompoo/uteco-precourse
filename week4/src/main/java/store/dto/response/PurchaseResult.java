package store.dto.response;

public record PurchaseResult(
        String productName,
        int purchaseAmount,
        int price,
        int promotionGetAmount
) {
}
