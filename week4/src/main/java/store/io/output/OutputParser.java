package store.io.output;

import java.util.Collections;
import java.util.List;
import java.util.StringJoiner;
import store.dto.response.ProductResponse;
import store.dto.response.PromotionedProductResponse;
import store.dto.response.PurchaseCostResponse;
import store.dto.response.PurchasedProductResponse;

public class OutputParser {

    private static final String RECEIPT_OUTPUT_FORMAT = "%-15s\t%-5s\t  %s";
    private static final String PRICE_FORMAT = "%,d";

    public String parseProductResponses(List<ProductResponse> productResponses) {
        Collections.sort(productResponses);
        StringBuilder stringBuilder = new StringBuilder();
        for (ProductResponse productResponse : productResponses) {
            StringJoiner stringJoiner = new StringJoiner(" ");
            stringBuilder.append("\n");
            stringJoiner.add("-");
            stringJoiner.add(productResponse.productName());
            stringJoiner.add(PRICE_FORMAT.formatted(productResponse.price()) + "원");
            stringJoiner.add(getStock(productResponse.stock()));
            if (!productResponse.promotionName().isBlank()) {
                stringJoiner.add(productResponse.promotionName());
            }
            stringBuilder.append(stringJoiner);
        }
        return stringBuilder.toString();
    }

    private static String getStock(int stock) {
        if (stock == 0) {
            return "재고 없음";
        }
        return stock + "개";
    }

    public String parsePurchasedProductsResponses(List<PurchasedProductResponse> purchasedProductResponses) {
        StringJoiner stringJoiner = new StringJoiner("\n");
        stringJoiner.add(RECEIPT_OUTPUT_FORMAT.formatted(
                "상품명",
                "수량",
                "금액"
        ));
        for (PurchasedProductResponse purchasedProductResponse : purchasedProductResponses) {
            stringJoiner.add(RECEIPT_OUTPUT_FORMAT.formatted(
                    purchasedProductResponse.productName(),
                    purchasedProductResponse.purchaseAmount(),
                    PRICE_FORMAT.formatted(
                            purchasedProductResponse.purchaseAmount() * purchasedProductResponse.price())
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
                PRICE_FORMAT.formatted(purchaseCostResponse.originalPurchaseCost())
        ));
        stringJoiner.add(RECEIPT_OUTPUT_FORMAT.formatted(
                "행사할인",
                "",
                "-" + PRICE_FORMAT.formatted(purchaseCostResponse.promotionSaleCost())
        ));
        stringJoiner.add(RECEIPT_OUTPUT_FORMAT.formatted(
                "멤버십할인",
                "",
                "-" + PRICE_FORMAT.formatted(purchaseCostResponse.membershipSaleCost())
        ));
        stringJoiner.add(RECEIPT_OUTPUT_FORMAT.formatted(
                "내실돈",
                "",
                " " + PRICE_FORMAT.formatted(purchaseCostResponse.finalPrice())
        ));
        return stringJoiner.toString();
    }
}
