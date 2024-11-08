package store.io.output;

import java.util.List;
import java.util.StringJoiner;
import store.dto.response.ProductResponse;
import store.dto.response.PromotionedProductResponse;
import store.dto.response.PurchaseCostResponse;
import store.dto.response.PurchasedProductResponse;

public class OutputParser {

    private static final String RECEIPT_OUTPUT_FORMAT = "%s\t\t\t\t%s\t\t  %s";

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
        StringJoiner stringJoiner = new StringJoiner("\n");
        for (PurchasedProductResponse purchasedProductResponse : purchasedProductResponses) {
            stringJoiner.add(RECEIPT_OUTPUT_FORMAT.formatted(
                    purchasedProductResponse.productName(),
                    purchasedProductResponse.purchaseAmount(),
                    purchasedProductResponse.purchaseAmount() * purchasedProductResponse.price()
            ));
        }
        return stringJoiner.toString();
    }

    public String parsePromotionedProductsResponses(List<PromotionedProductResponse> promotionedProductResponses) {
        StringJoiner stringJoiner = new StringJoiner("\n");
        for (PromotionedProductResponse promotionedProductResponse : promotionedProductResponses) {
            stringJoiner.add(RECEIPT_OUTPUT_FORMAT.formatted(
                    promotionedProductResponse.productName(),
                    promotionedProductResponse.promotionedAmount(),
                    ""
            ));
        }
        return stringJoiner.toString();
    }

    public String parsePurchaseCostResponse(PurchaseCostResponse purchaseCostResponse) {
        StringJoiner stringJoiner = new StringJoiner("\n");
        stringJoiner.add(RECEIPT_OUTPUT_FORMAT.formatted(
                "총구매액",
                purchaseCostResponse.purchaseAmount(),
                purchaseCostResponse.originalPurchaseCost()
        ));
        stringJoiner.add(RECEIPT_OUTPUT_FORMAT.formatted(
                "행사할인",
                "",
                "-" + purchaseCostResponse.promotionSaleCost()
        ));
        stringJoiner.add(RECEIPT_OUTPUT_FORMAT.formatted(
                "멤버십할인",
                "",
                "-" + purchaseCostResponse.membershipSaleCost()
        ));
        stringJoiner.add(RECEIPT_OUTPUT_FORMAT.formatted(
                "내실돈",
                "",
                purchaseCostResponse.finalPrice()
        ));
        return stringJoiner.toString();
    }
}
