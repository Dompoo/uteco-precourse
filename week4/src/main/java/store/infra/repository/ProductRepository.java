package store.infra.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import store.domain.Product;
import store.exception.StoreExceptions;
import store.infra.database.Database;
import store.infra.entity.ProductEntity;
import store.infra.entity.PromotionEntity;
import store.infra.repository.convertor.ProductConverter;
import store.service.dateProvider.DateProvider;

public class ProductRepository implements Repository<Product> {

    private final List<Product> products = new ArrayList<>();

    public ProductRepository(
            Database<ProductEntity> productDatabase,
            Database<PromotionEntity> promotionDatabase,
            ProductConverter productConverter,
            DateProvider dateProvider
    ) {
        List<PromotionEntity> promotionEntities = promotionDatabase.readAll().stream()
                .filter(promotionEntity -> promotionEntity.isAvailable(dateProvider.getDate()))
                .toList();
        this.products.addAll(productConverter.convert(productDatabase.readAll(), promotionEntities));
    }

    @Override
    public List<Product> findAll() {
        return products;
    }

    @Override
    public Optional<Product> findByName(String name) {
        for (Product product : products) {
            if (product.getName().equals(name)) {
                return Optional.of(product);
            }
        }
        return Optional.empty();
    }

    @Override
    public void update(Product product) {
        Product findProduct = products.stream()
                .filter(products -> products.getName().equals(product.getName()))
                .findFirst()
                .orElseThrow(StoreExceptions.PRODUCT_NOT_FOUND::get);

        products.remove(findProduct);
        products.add(product);
    }
}
