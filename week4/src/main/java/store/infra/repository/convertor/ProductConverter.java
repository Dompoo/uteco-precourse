package store.infra.repository.convertor;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import store.domain.Product;
import store.domain.ProductBuilder;
import store.infra.entity.ProductEntity;
import store.infra.entity.PromotionEntity;

public class ProductConverter {

    public List<Product> convert(List<ProductEntity> productEntities, List<PromotionEntity> promotionEntities,
                                 LocalDate now) {
        Map<String, PromotionEntity> promotionMap = convertPromotionEntitiesToMap(promotionEntities);
        productEntities = update(productEntities, promotionMap);
        Map<String, ProductBuilder> productMap = new HashMap<>();
        for (ProductEntity productEntity : productEntities) {
            ProductBuilder productBuilder = getProductBuilder(productEntity, productMap, now);
            updateProductBuilder(productEntity, productBuilder, promotionMap);
            productMap.put(productEntity.name(), productBuilder);
        }
        return convertToProducts(productMap);
    }

    private static List<ProductEntity> update(List<ProductEntity> productEntities,
                                               Map<String, PromotionEntity> promotionMap) {
        return productEntities.stream()
                .filter(productEntity -> !productEntity.isPromotionStockEntity()
                        || promotionMap.containsKey(productEntity.promotionName())).toList();
    }

    private static Map<String, PromotionEntity> convertPromotionEntitiesToMap(List<PromotionEntity> promotionEntities) {
        return promotionEntities.stream()
                .collect(Collectors.toMap(
                        PromotionEntity::name,
                        promotionEntity -> promotionEntity)
                );
    }

    private static ProductBuilder getProductBuilder(
            ProductEntity productEntity,
            Map<String, ProductBuilder> products,
            LocalDate now
    ) {
        String name = productEntity.name();
        if (products.containsKey(name)) {
            return products.get(name);
        }
        return new ProductBuilder(now)
                .setName(productEntity.name())
                .setPrice(productEntity.price());
    }

    private static void updateProductBuilder(
            ProductEntity productEntity,
            ProductBuilder productBuilder,
            Map<String, PromotionEntity> promotionMap
    ) {
        if (productEntity.isPromotionStockEntity()) {
            productBuilder
                    .setPromotionStock(productEntity.quantity())
                    .setPromotion(promotionMap.get(productEntity.promotionName()));
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
