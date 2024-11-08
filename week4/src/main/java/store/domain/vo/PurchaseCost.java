package store.domain.vo;

public record PurchaseCost(
        int originalPurchaseCost,
        int promotionSaleCost,
        int membershipSaleCost,
        int finalPrice
) {
}
