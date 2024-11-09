package store.infra.repository.convertor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import store.domain.Product;
import store.domain.Product.ProductBuilder;
import store.domain.Promotion;
import store.infra.entity.ProductEntity;

public class ProductConverter {

    public List<Product> convert(List<ProductEntity> productEntities, List<Promotion> promotions) {
        Map<String, Promotion> promotionMap = convertPromotionsToMap(promotions);
        Map<String, ProductBuilder> productMap = new HashMap<>();
        for (ProductEntity productEntity : productEntities) {
            ProductBuilder productBuilder = getProductBuilder(productEntity, productMap);
            updateProductBuilder(productEntity, productBuilder, promotionMap);
            productMap.put(productEntity.name(), productBuilder);
        }
        return convertToProducts(productMap);
    }

    private static Map<String, Promotion> convertPromotionsToMap(List<Promotion> promotions) {
        return promotions.stream()
                .collect(Collectors.toMap(
                        Promotion::getName,
                        promotion -> promotion)
                );
    }

    private static ProductBuilder getProductBuilder(
            ProductEntity productEntity,
            Map<String, ProductBuilder> products
    ) {
        String name = productEntity.name();
        if (products.containsKey(name)) {
            return products.get(name);
        }
        return new ProductBuilder()
                .setName(productEntity.name())
                .setPrice(productEntity.price());
    }

    private static void updateProductBuilder(
            ProductEntity productEntity,
            ProductBuilder productBuilder,
            Map<String, Promotion> promotionMap
    ) {
        if (productEntity.isPromotionStockEntity()) {
            productBuilder.setPromotion(promotionMap.get(productEntity.promotionName()))
                    .setPromotionStock(productEntity.quantity());
            return;
        }
        productBuilder.setDefaultStock(productEntity.quantity());
    }

    private static List<Product> convertToProducts(Map<String, ProductBuilder> products) {
        return products.values().stream()
                .map(ProductBuilder::build)
                .toList();
    }
}
