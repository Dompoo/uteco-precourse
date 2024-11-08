package store.infra.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import store.domain.Product;
import store.domain.Promotion;
import store.infra.database.Database;
import store.infra.entity.ProductEntity;
import store.infra.entity.PromotionEntity;
import store.infra.repository.convertor.ProductConverter;
import store.infra.repository.convertor.ProductEntityConverter;
import store.infra.repository.convertor.PromotionConverter;
import store.infra.repository.convertor.PromotionEntityConverter;

public class ProductRepository implements Repository<Product> {

    private final Database<ProductEntity> productDatabase;
    private final Database<PromotionEntity> promotionDatabase;
    private final ProductEntityConverter productEntityConverter;
    private final PromotionEntityConverter promotionEntityConverter;
    private final List<Product> products = new ArrayList<>();

    public ProductRepository(
            Database<ProductEntity> productDatabase,
            Database<PromotionEntity> promotionDatabase,
            ProductConverter productConverter,
            PromotionConverter promotionConverter,
            ProductEntityConverter productEntityConverter,
            PromotionEntityConverter promotionEntityConverter
    ) {
        this.productDatabase = productDatabase;
        this.promotionDatabase = promotionDatabase;
        this.productEntityConverter = productEntityConverter;
        this.promotionEntityConverter = promotionEntityConverter;
        List<Promotion> promotions = promotionConverter.convert(promotionDatabase.readAll());
        this.products.addAll(productConverter.convert(productDatabase.readAll(), promotions));
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
                .orElseThrow();

        products.remove(findProduct);
        products.add(product);
    }

    @Override
    public void updateDatabaseInBatch() {
        productDatabase.updateAll(productEntityConverter.convert(products));
        promotionDatabase.updateAll(promotionEntityConverter.convert(products));
    }
}
