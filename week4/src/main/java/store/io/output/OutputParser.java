package store.io.output;

import java.util.List;
import java.util.StringJoiner;
import store.dto.response.ProductResponse;
import store.dto.response.PromotionedProductResponse;
import store.dto.response.PurchaseCostResponse;
import store.dto.response.PurchasedProductResponse;

public class OutputParser {

    private static final String PURCHASED_PRODUCT_FORMAT = "%-20s %8s %12s%n";
    private static final String PROMOTIONED_PRODUCT_FORMAT = "%-20s %8s%n";
    private static final String PURCHASED_COST_FORMAT = "%-20s%n";

    public String parseProductResponses(List<ProductResponse> productResponses) {
        StringBuilder stringBuilder = new StringBuilder();
        for (ProductResponse productResponse : productResponses) {
            StringJoiner stringJoiner = new StringJoiner(" ");
            stringJoiner.add("-");
            stringJoiner.add(productResponse.productName());
            stringJoiner.add(productResponse.price() + "원");

            stringJoiner.add(productResponse.stock() + "개");
            if (!productResponse.promotionName().isBlank()) {
                stringJoiner.add(productResponse.promotionName());
            }
            stringBuilder.append(stringJoiner).append("\n");
        }
        return stringBuilder.toString();
    }

    public String parsePurchasedProductsResponses(List<PurchasedProductResponse> purchasedProductResponses) {
        StringBuilder stringBuilder = new StringBuilder();
        for (PurchasedProductResponse purchasedProductResponse : purchasedProductResponses) {
            stringBuilder.append(PURCHASED_PRODUCT_FORMAT.formatted(
                    purchasedProductResponse.productName(),
                    purchasedProductResponse.purchaseAmount(),
                    purchasedProductResponse.purchaseAmount() * purchasedProductResponse.price()
            ));
        }
        return stringBuilder.toString();
    }

    public String parsePromotionedProductsResponses(List<PromotionedProductResponse> promotionedProductResponses) {
        StringBuilder stringBuilder = new StringBuilder();
        for (PromotionedProductResponse promotionedProductResponse : promotionedProductResponses) {
            stringBuilder.append(PROMOTIONED_PRODUCT_FORMAT.formatted(
                    promotionedProductResponse.productName(),
                    promotionedProductResponse.promotionedAmount()
            ));
        }
        return stringBuilder.toString();
    }

    public String parsePurchaseCostResponse(PurchaseCostResponse purchaseCostResponse) {
        StringJoiner stringJoiner = new StringJoiner("\n");
        stringJoiner.add(PROMOTIONED_PRODUCT_FORMAT.formatted(
                purchaseCostResponse.originalPurchaseCost(),
                purchaseCostResponse.purchaseAmount()
        ));
        stringJoiner.add(PURCHASED_COST_FORMAT.formatted(purchaseCostResponse.promotionSaleCost()));
        stringJoiner.add(PURCHASED_COST_FORMAT.formatted(purchaseCostResponse.membershipSaleCost()));
        stringJoiner.add(PURCHASED_COST_FORMAT.formatted(purchaseCostResponse.finalPrice()));
        return stringJoiner.toString();
    }
}
