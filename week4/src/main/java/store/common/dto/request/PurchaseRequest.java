package store.common.dto.request;

public record PurchaseRequest(
        String productName,
        int purchaseAmount
) {
}
