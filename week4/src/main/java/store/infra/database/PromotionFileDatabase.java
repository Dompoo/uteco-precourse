package store.infra.database;

import java.util.Map;
import store.infra.entity.PromotionEntity;

public class PromotionFileDatabase extends FileDatabase<PromotionEntity> {

    private static final String PROMOTION_FILE_PATH = "src/main/resources/promotions.md";

    @Override
    protected PromotionEntity convertLineToObject(Map<String, String> dataMap) {
        return PromotionEntity.from(dataMap);
    }

    @Override
    protected String getFilePath() {
        return PROMOTION_FILE_PATH;
    }
}
