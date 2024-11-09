package store.dto.response;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import store.domain.Product;

public record ProductResponse(
        String productName,
        int price,
        int stock,
        String promotionName
) implements Comparable<ProductResponse> {
    public static List<ProductResponse> fromList(List<Product> products, LocalDate localDate) {
        List<ProductResponse> productResponses = new ArrayList<>();
        for (Product product : products) {
            String productName = product.getName();
            int price = product.getPrice();
            productResponses.add(new ProductResponse(productName, price, product.getDefaultStock(localDate), ""));
            if (product.hasPromotion(localDate)) {
                int promotionStock = product.getPromotionStock(localDate);
                String promotionName = product.getPromotion().getName();
                productResponses.add(new ProductResponse(productName, price, promotionStock, promotionName));
            }
        }
        return productResponses;
    }

    @Override
    public int compareTo(ProductResponse o) {
        return this.toString().compareTo(o.toString());
    }
}
