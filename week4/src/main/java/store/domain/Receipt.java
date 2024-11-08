package store.domain;

import java.util.ArrayList;
import java.util.List;
import store.domain.membership.Membership;
import store.domain.membership.NoMembership;
import store.domain.vo.PromotionedProduct;
import store.domain.vo.PurchaseCost;
import store.domain.vo.PurchasedProduct;
import store.dto.response.PurchaseResult;

final public class Receipt {

    private final List<PurchasedProduct> purchasedProducts;
    private final List<PromotionedProduct> promotionedProducts;
    private final PurchaseCost purchaseCost;

    public Receipt(List<PurchasedProduct> purchasedProducts, List<PromotionedProduct> promotionedProducts,
                   PurchaseCost purchaseCost) {
        this.purchasedProducts = purchasedProducts;
        this.promotionedProducts = promotionedProducts;
        this.purchaseCost = purchaseCost;
    }

    public List<PurchasedProduct> getPurchasedProducts() {
        return List.copyOf(purchasedProducts);
    }

    public List<PromotionedProduct> getPromotionedProducts() {
        return List.copyOf(promotionedProducts);
    }

    public PurchaseCost getPurchaseCost() {
        return purchaseCost;
    }

    public static class ReceiptBuilder {

        private final List<PurchaseResult> purchaseResults = new ArrayList<>();
        private Membership membership = new NoMembership();

        public void addPurchase(PurchaseResult purchaseResult) {
            this.purchaseResults.add(purchaseResult);
        }

        public void addMembership(Membership membership) {
            this.membership = membership;
        }

        public Receipt build() {
            List<PurchasedProduct> purchasedProducts = buildPurchasedProducts();
            List<PromotionedProduct> promotionedProducts = buildPromotionedProducts();
            PurchaseCost purchaseCost = buildPurchaseCost(purchasedProducts, promotionedProducts);

            return new Receipt(
                    purchasedProducts,
                    promotionedProducts,
                    purchaseCost
            );
        }

        private List<PurchasedProduct> buildPurchasedProducts() {
            List<PurchasedProduct> purchasedProducts = new ArrayList<>();
            for (PurchaseResult purchaseResult : this.purchaseResults) {
                String productName = purchaseResult.productName();
                int price = purchaseResult.price();
                int purchaseAmount = purchaseResult.purchaseAmount();
                purchasedProducts.add(new PurchasedProduct(productName, purchaseAmount, price));
            }
            return purchasedProducts;
        }

        private List<PromotionedProduct> buildPromotionedProducts() {
            List<PromotionedProduct> promotionGets = new ArrayList<>();
            for (PurchaseResult purchaseResult : this.purchaseResults) {
                String productName = purchaseResult.productName();
                int price = purchaseResult.price();
                int promotionGetAmount = purchaseResult.promotionGetAmount();
                if (promotionGetAmount != 0) {
                    promotionGets.add(new PromotionedProduct(productName, promotionGetAmount, price));
                }
            }
            return promotionGets;
        }

        private PurchaseCost buildPurchaseCost(
                List<PurchasedProduct> purchasedProducts,
                List<PromotionedProduct> promotionedProducts
        ) {
            int originalPurchaseCost = originalPurchaseCost(purchasedProducts);
            int promotionSaleCost = calculatePromotionSaleCost(promotionedProducts);
            int membershipSaleCost = membership.calculateMembershipSaleAmount(purchaseResults);
            int finalPrice = calculateFinalPrice(originalPurchaseCost, promotionSaleCost, membershipSaleCost);
            return new PurchaseCost(originalPurchaseCost, promotionSaleCost, membershipSaleCost, finalPrice);
        }

        private int originalPurchaseCost(List<PurchasedProduct> purchasedProducts) {
            int purchaseCost = 0;
            for (PurchasedProduct purchasedProduct : purchasedProducts) {
                purchaseCost += purchasedProduct.price() * purchasedProduct.purchaseAmount();
            }
            return purchaseCost;
        }

        private int calculatePromotionSaleCost(List<PromotionedProduct> promotionedProducts) {
            int saleCost = 0;
            for (PromotionedProduct promotionedProduct : promotionedProducts) {
                saleCost += promotionedProduct.price() * promotionedProduct.promotionedAmount();
            }
            return saleCost;
        }

        private int calculateFinalPrice(int originalPurchaseCost, int promotionSaleCost, int membershipSaleCost) {
            return originalPurchaseCost - promotionSaleCost - membershipSaleCost;
        }
    }
}
