package store.infra.database;

import java.util.Map;
import store.infra.entity.ProductEntity;

public class ProductFileDatabase extends FileDatabase<ProductEntity> {

    private static final String PRODUCT_FILE_PATH = "src/main/resources/products.md";

    @Override
    protected ProductEntity convertLineToObject(Map<String, String> dataMap) {
        return ProductEntity.from(dataMap);
    }

    @Override
    protected String getFilePath() {
        return PRODUCT_FILE_PATH;
    }
}
