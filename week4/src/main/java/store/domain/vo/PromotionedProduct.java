package store.domain.vo;

public record PromotionedProduct(
        String productName,
        int promotionedAmount,
        int price
) {
}
