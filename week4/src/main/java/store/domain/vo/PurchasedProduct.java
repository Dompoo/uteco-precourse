package store.domain.vo;

public record PurchasedProduct(
        String productName,
        int purchaseAmount,
        int price
) {
}
