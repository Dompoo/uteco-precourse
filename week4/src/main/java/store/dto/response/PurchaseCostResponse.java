package store.dto.response;

import store.domain.Receipt;
import store.domain.vo.PurchaseCost;

public record PurchaseCostResponse(
        int originalPurchaseCost,
        int purchaseAmount,
        int promotionSaleCost,
        int membershipSaleCost,
        int finalPrice
) {
    public static PurchaseCostResponse from(Receipt receipt) {
        PurchaseCost purchaseCost = receipt.getPurchaseCost();
        return new PurchaseCostResponse(
                purchaseCost.originalPurchaseCost(),
                receipt.getPurchasedProducts().size(),
                purchaseCost.promotionSaleCost(),
                purchaseCost.membershipSaleCost(),
                purchaseCost.finalPrice()
        );
    }
}
