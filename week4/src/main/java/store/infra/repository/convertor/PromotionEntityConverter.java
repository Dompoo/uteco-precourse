package store.infra.repository.convertor;

import java.util.List;
import java.util.stream.Collectors;
import store.domain.Product;
import store.infra.entity.PromotionEntity;

public class PromotionEntityConverter {

    public List<PromotionEntity> convert(List<Product> products) {
        return products.stream()
                .map(Product::getPromotion)
                .map(PromotionEntity::from)
                .collect(Collectors.toSet())
                .stream()
                .toList();
    }
}
