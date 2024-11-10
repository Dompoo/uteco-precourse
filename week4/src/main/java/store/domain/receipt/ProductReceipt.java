package store.domain.receipt;

import java.util.ArrayList;
import java.util.List;
import store.common.dto.response.PurchaseResult;
import store.common.dto.response.PurchasedProductResponse;

final public class ProductReceipt {

    private final List<PurchaseResult> purchaseResults = new ArrayList<>();

    public void addPurchase(PurchaseResult purchaseResult) {
        this.purchaseResults.add(purchaseResult);
    }

    public List<PurchasedProductResponse> buildPurchasedProductResponses() {
        List<PurchasedProductResponse> purchasedProducts = new ArrayList<>();
        for (PurchaseResult purchaseResult : this.purchaseResults) {
            String productName = purchaseResult.productName();
            int price = purchaseResult.price();
            int purchaseAmount = purchaseResult.purchaseAmount();
            purchasedProducts.add(new PurchasedProductResponse(productName, purchaseAmount, price));
        }
        return purchasedProducts;
    }
}
