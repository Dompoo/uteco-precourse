package store.infra.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import store.domain.Product;
import store.domain.Promotion;
import store.exception.StoreExceptions;
import store.infra.database.Database;
import store.infra.entity.ProductEntity;
import store.infra.entity.PromotionEntity;
import store.infra.repository.convertor.ProductConverter;
import store.infra.repository.convertor.PromotionConverter;

public class ProductRepository implements Repository<Product> {

    private final Database<ProductEntity> productDatabase;
    private final Database<PromotionEntity> promotionDatabase;
    private final List<Product> products = new ArrayList<>();

    public ProductRepository(
            Database<ProductEntity> productDatabase,
            Database<PromotionEntity> promotionDatabase,
            ProductConverter productConverter,
            PromotionConverter promotionConverter
    ) {
        this.productDatabase = productDatabase;
        this.promotionDatabase = promotionDatabase;
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
                .orElseThrow(StoreExceptions.PRODUCT_NOT_FOUND::get);

        products.remove(findProduct);
        products.add(product);
    }
}
