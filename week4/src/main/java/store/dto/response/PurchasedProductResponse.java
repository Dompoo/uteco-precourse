package store.dto.response;

import java.util.ArrayList;
import java.util.List;
import store.domain.Receipt;
import store.domain.vo.PurchasedProduct;

public record PurchasedProductResponse(
        String productName,
        int purchaseAmount,
        int price
) {
    public static List<PurchasedProductResponse> from(Receipt receipt) {
        List<PurchasedProductResponse> purchasedProductResponses = new ArrayList<>();
        for (PurchasedProduct purchasedProduct : receipt.getPurchasedProducts()) {
            purchasedProductResponses.add(new PurchasedProductResponse(
                    purchasedProduct.productName(),
                    purchasedProduct.purchaseAmount(),
                    purchasedProduct.price()
            ));
        }
        return purchasedProductResponses;
    }
}
