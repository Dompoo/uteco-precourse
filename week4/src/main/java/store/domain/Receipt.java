package store.domain;

import java.util.List;
import store.domain.membership.Membership;
import store.dto.response.PromotionedProductResponse;
import store.dto.response.PurchaseCostResponse;
import store.dto.response.PurchaseResult;
import store.dto.response.PurchasedProductResponse;

public class Receipt {

    private final ProductReceipt productReceipt;
    private final PromotionReceipt promotionReceipt;
    private final CostReceipt costReceipt;

    public Receipt() {
        this.productReceipt = new ProductReceipt();
        this.promotionReceipt = new PromotionReceipt();
        this.costReceipt = new CostReceipt();
    }

    public void addPurchase(PurchaseResult purchaseResult) {
        this.productReceipt.addPurchase(purchaseResult);
        this.promotionReceipt.addPurchase(purchaseResult);
        this.costReceipt.addPurchase(purchaseResult);
    }

    public void addMembership(Membership membership) {
        this.costReceipt.addMembership(membership);
    }

    public List<PurchasedProductResponse> buildPurchasedProductResponses() {
        return this.productReceipt.buildPurchasedProductResponses();
    }

    public List<PromotionedProductResponse> buildPromotionedProductResponses() {
        return this.promotionReceipt.buildPromotionedProductResponses();
    }

    public PurchaseCostResponse buildPurchaseCostResponse() {
        return this.costReceipt.buildPurchaseCostResponse();
    }
}
