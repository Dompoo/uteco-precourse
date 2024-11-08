package store.dto.response;

import java.util.ArrayList;
import java.util.List;
import store.domain.Product;

public record ProductResponse(
        String productName,
        int price,
        int stock,
        String promotionName
) {
    public static List<ProductResponse> fromList(List<Product> products) {
        List<ProductResponse> productResponses = new ArrayList<>();
        for (Product product : products) {
            String productName = product.getName();
            int price = product.getPrice();
            if (product.hasDefaultStock()) {
                int defaultStock = product.getDefaultStock();
                productResponses.add(new ProductResponse(productName, price, defaultStock, ""));
            }
            if (product.hasPromotionStock()) {
                int promotionStock = product.getPromotionStock();
                String promotionName = product.getPromotion().getName();
                productResponses.add(new ProductResponse(productName, price, promotionStock, promotionName));
            }
        }
        return productResponses;
    }
}
