package store.dto.response;

import java.util.ArrayList;
import java.util.List;
import store.domain.Receipt;
import store.domain.vo.PromotionedProduct;

public record PromotionedProductResponse(
        String productName,
        int promotionedAmount
) {
    public static List<PromotionedProductResponse> from(Receipt receipt) {
        List<PromotionedProductResponse> promotionedProductResponses = new ArrayList<>();
        for (PromotionedProduct promotionedProduct : receipt.getPromotionedProducts()) {
            promotionedProductResponses.add(new PromotionedProductResponse(
                    promotionedProduct.productName(),
                    promotionedProduct.promotionedAmount()
            ));
        }
        return promotionedProductResponses;
    }
}
