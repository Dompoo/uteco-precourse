package store.infra.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;
import store.domain.Product;

public record ProductEntity(
        String name,
        int price,
        int quantity,
        String promotionName
) implements DatabaseEntity {

    public static ProductEntity from(Map<String, String> dataMap) {
        String name = dataMap.get("name");
        int price = Integer.parseInt(dataMap.get("price"));
        int stock = Integer.parseInt(dataMap.get("quantity"));
        String promotionName = dataMap.get("promotion");
        if (promotionName.equals("null")) {
            return new ProductEntity(name, price, stock, "");
        }
        return new ProductEntity(name, price, stock, promotionName);
    }

    public static Stream<ProductEntity> from(Product product) {
        List<ProductEntity> productEntities = new ArrayList<>();
        String name = product.getName();
        int price = product.getPrice();
        int stock = product.getDefaultStock();
        String promotionName = "null";
        productEntities.add(new ProductEntity(name, price, stock, promotionName));
        if (product.hasPromotion()) {
            productEntities.add(createPromotionProduct(product, name, price));
        }
        return productEntities.stream();
    }

    private static ProductEntity createPromotionProduct(
            Product product,
            String name,
            int price
    ) {
        int stock = product.getPromotionStock();
        String promotionName = product.getPromotion().getName();
        return new ProductEntity(name, price, stock, promotionName);
    }

    public boolean isPromotionStockEntity() {
        return promotionName != null && !promotionName.isBlank();
    }

    @Override
    public String toLine(String[] columns) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String column : columns) {
            if (column.equals("name")) {
                stringBuilder.append(name);
            }
            if (column.equals("price")) {
                stringBuilder.append(price);
            }
            if (column.equals("quantity")) {
                stringBuilder.append(quantity);
            }
            if (column.equals("promotionName")) {
                stringBuilder.append(promotionName);
            }
        }
        return stringBuilder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductEntity that)) {
            return false;
        }
        return price == that.price && Objects.equals(name, that.name) && Objects.equals(promotionName,
                that.promotionName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price, promotionName);
    }
}
