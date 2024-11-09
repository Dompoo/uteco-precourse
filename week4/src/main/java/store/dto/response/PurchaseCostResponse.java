package store.dto.response;

import store.domain.Receipt;
import store.domain.vo.PurchaseCost;
import store.domain.vo.PurchasedProduct;

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
                calculateTotalPuchaseAmount(receipt),
                purchaseCost.promotionSaleCost(),
                purchaseCost.membershipSaleCost(),
                purchaseCost.finalPrice()
        );
    }

    private static int calculateTotalPuchaseAmount(Receipt receipt) {
        return receipt.getPurchasedProducts().stream()
                .mapToInt(PurchasedProduct::purchaseAmount)
                .sum();
    }
}
