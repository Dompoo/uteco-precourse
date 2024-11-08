package store.dto.response;

import java.util.ArrayList;
import java.util.List;
import store.domain.Product;
import store.domain.StockType;

public record ProductResponse(
        String productName,
        int price,
        int stock,
        String promotionName
) {
    public static List<ProductResponse> fromList(List<Product> products) {
        List<ProductResponse> productResponses = new ArrayList<>();
        for (Product product : products) {
            StockType productStockType = product.getStockType();
            String productName = product.getName();
            int price = product.getPrice();
            if (productStockType == StockType.DEFAULT_ONLY
                    || productStockType == StockType.DEFAULT_AND_PROMOTION) {
                int defaultStock = product.getDefaultStock();
                productResponses.add(new ProductResponse(productName, price, defaultStock, ""));
            }
            if (productStockType == StockType.PROMOTION_ONLY
                    || productStockType == StockType.DEFAULT_AND_PROMOTION) {
                int promotionStock = product.getPromotionStock();
                String promotionName = product.getPromotion().getName();
                productResponses.add(new ProductResponse(productName, price, promotionStock, promotionName));
            }
        }
        return productResponses;
    }
}
