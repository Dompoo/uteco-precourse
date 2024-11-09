package store.dto.response;

import store.domain.Product;
import store.domain.vo.PurchaseStatus;

public record PurchaseResult(
        String productName,
        int purchaseAmount,
        int promotionedProductAmount,
        int price,
        int promotionGetAmount
) {
    public static PurchaseResult of(Product product, PurchaseStatus purchaseStatus) {
        return new PurchaseResult(
                product.getName(),
                purchaseStatus.finalPurchaseAmount(),
                purchaseStatus.promotionedProductAmount(),
                product.getPrice(),
                purchaseStatus.promotionGetAmount()
        );
    }

    public int getDefaultPurchasePrice() {
        return (purchaseAmount - promotionedProductAmount) * price;
    }
}
