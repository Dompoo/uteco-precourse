package store.infra.repository.convertor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import store.domain.Product;
import store.domain.ProductBuilder;
import store.domain.PromotionType;
import store.infra.entity.ProductEntity;
import store.infra.entity.PromotionEntity;

public class ProductConverter {

    public List<Product> convert(List<ProductEntity> productEntities, List<PromotionEntity> promotionEntities) {
        Map<String, PromotionEntity> promotionMap = convertPromotionEntitiesToMap(promotionEntities);
        productEntities = update(productEntities, promotionMap);
        Map<String, ProductBuilder> productMap = new HashMap<>();
        for (ProductEntity productEntity : productEntities) {
            ProductBuilder productBuilder = getProductBuilder(productEntity, productMap);
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
            Map<String, ProductBuilder> products
    ) {
        String name = productEntity.name();
        if (products.containsKey(name)) {
            return products.get(name);
        }
        return new ProductBuilder()
                .setName(productEntity.name())
                .setPrice(productEntity.price())
                .setPromotionName("")
                .setPromotionType(PromotionType.NO_PROMOTION);
    }

    private static void updateProductBuilder(
            ProductEntity productEntity,
            ProductBuilder productBuilder,
            Map<String, PromotionEntity> promotionMap
    ) {
        if (productEntity.isPromotionStockEntity()) {
            PromotionEntity promotionEntity = promotionMap.get(productEntity.promotionName());
            productBuilder
                    .setPromotionName(productEntity.promotionName())
                    .setPromotionType(PromotionType.of(promotionEntity.buy(), promotionEntity.get()))
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
