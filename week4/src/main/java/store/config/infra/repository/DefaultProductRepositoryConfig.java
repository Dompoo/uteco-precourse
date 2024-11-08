package store.config.infra.repository;

import store.config.infra.database.ProductDatabaseConfig;
import store.config.infra.database.PromotionDatabaseConfig;
import store.domain.Product;
import store.infra.repository.ProductRepository;
import store.infra.repository.Repository;
import store.infra.repository.convertor.ProductConverter;
import store.infra.repository.convertor.ProductEntityConverter;
import store.infra.repository.convertor.PromotionConverter;
import store.infra.repository.convertor.PromotionEntityConverter;

public class DefaultProductRepositoryConfig implements ProductRepositoryConfig {

    private final Repository<Product> productRepository;

    public DefaultProductRepositoryConfig(
            ProductDatabaseConfig productDatabaseConfig,
            PromotionDatabaseConfig promotionDatabaseConfig
    ) {
        this.productRepository = new ProductRepository(
                productDatabaseConfig.getProductDatabase(),
                promotionDatabaseConfig.getPromotionDatabase(),
                new ProductConverter(),
                new PromotionConverter(),
                new ProductEntityConverter(),
                new PromotionEntityConverter()
        );
    }

    @Override
    public Repository<Product> getProductRepository() {
        return this.productRepository;
    }
}
