package store.io.output;

import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import store.dto.response.ProductResponse;
import store.dto.response.PromotionedProductResponse;
import store.dto.response.PurchaseCostResponse;
import store.dto.response.PurchasedProductResponse;

public class OutputParser {

    private static final String NEW_LINE = "\n";
    private static final String PRICE_FORMAT = "%,d";
    private static final String RECEIPT_OUTPUT_FORMAT = "%-15s\t%-5s\t  %s";

    public String parseProductResponses(List<ProductResponse> productResponses) {
        return productResponses.stream()
                .sorted()
                .map(OutputParser::mapProductResponseToString)
                .collect(Collectors.joining(NEW_LINE));
    }

    private static String mapProductResponseToString(ProductResponse productResponse) {
        return String.format("- %s %s원 %s %s",
                productResponse.productName(),
                PRICE_FORMAT.formatted(productResponse.price()),
                getStock(productResponse),
                getPromotionName(productResponse));
    }

    private static String getStock(ProductResponse productResponse) {
        int stock = productResponse.stock();
        if (stock == 0) {
            return "재고 없음";
        }
        return stock + "개";
    }

    private static String getPromotionName(ProductResponse productResponse) {
        String promotionName = productResponse.promotionName();
        if (promotionName == null || promotionName.isBlank()) {
            return "";
        }
        return promotionName;
    }

    public String parsePurchasedProductsResponses(List<PurchasedProductResponse> purchasedProductResponses) {
        return Stream.concat(
                Stream.of(RECEIPT_OUTPUT_FORMAT.formatted("상품명", "수량", "금액")),
                purchasedProductResponses.stream().map(OutputParser::mapPurchasedProductResponseToString)
        ).collect(Collectors.joining(NEW_LINE));
    }

    private static String mapPurchasedProductResponseToString(PurchasedProductResponse response) {
        return RECEIPT_OUTPUT_FORMAT.formatted(
                response.productName(),
                response.purchaseAmount(),
                PRICE_FORMAT.formatted(getPurchasePrice(response)));
    }

    private static int getPurchasePrice(PurchasedProductResponse response) {
        return response.purchaseAmount() * response.price();
    }

    public String parsePromotionedProductsResponses(List<PromotionedProductResponse> promotionedProductResponses) {
        return promotionedProductResponses.stream()
                .map(OutputParser::mapPromotionedProductResponseToString)
                .collect(Collectors.joining(NEW_LINE));
    }

    private static String mapPromotionedProductResponseToString(PromotionedProductResponse response) {
        return RECEIPT_OUTPUT_FORMAT.formatted(
                response.productName(),
                response.promotionedAmount(),
                "");
    }

    public String parsePurchaseCostResponse(PurchaseCostResponse purchaseCostResponse) {
        StringJoiner stringJoiner = new StringJoiner(NEW_LINE);
        stringJoiner.add(mapOriginalPurchaseCost(purchaseCostResponse));
        stringJoiner.add(mapPromotionSaleCost(purchaseCostResponse));
        stringJoiner.add(mapMembershipSaleCost(purchaseCostResponse));
        stringJoiner.add(mapFinalPrice(purchaseCostResponse));
        return stringJoiner.toString();
    }

    private static String mapOriginalPurchaseCost(PurchaseCostResponse purchaseCostResponse) {
        return RECEIPT_OUTPUT_FORMAT.formatted(
                "총구매액",
                purchaseCostResponse.purchaseAmount(),
                PRICE_FORMAT.formatted(purchaseCostResponse.originalPurchaseCost())
        );
    }

    private static String mapPromotionSaleCost(PurchaseCostResponse purchaseCostResponse) {
        return RECEIPT_OUTPUT_FORMAT.formatted(
                "행사할인",
                "",
                "-" + PRICE_FORMAT.formatted(purchaseCostResponse.promotionSaleCost())
        );
    }

    private static String mapMembershipSaleCost(PurchaseCostResponse purchaseCostResponse) {
        return RECEIPT_OUTPUT_FORMAT.formatted(
                "멤버십할인",
                "",
                "-" + PRICE_FORMAT.formatted(purchaseCostResponse.membershipSaleCost())
        );
    }

    private static String mapFinalPrice(PurchaseCostResponse purchaseCostResponse) {
        return RECEIPT_OUTPUT_FORMAT.formatted(
                "내실돈",
                "",
                " " + PRICE_FORMAT.formatted(purchaseCostResponse.finalPrice())
        );
    }
}
