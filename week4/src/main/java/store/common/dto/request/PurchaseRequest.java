package store.common.dto.request;

public record PurchaseRequest(
        String productName,
        int purchaseAmount
) {
    public PurchaseRequest add(PurchaseRequest other) {
        return new PurchaseRequest(other.productName, this.purchaseAmount + other.purchaseAmount);
    }
}
