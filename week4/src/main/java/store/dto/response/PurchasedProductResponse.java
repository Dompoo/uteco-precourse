package store.dto.response;


public record PurchasedProductResponse(
        String productName,
        int purchaseAmount,
        int price
) {
}
