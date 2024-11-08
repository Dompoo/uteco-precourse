package store.infra.repository.convertor;

import java.util.ArrayList;
import java.util.List;
import store.domain.Promotion;
import store.infra.entity.PromotionEntity;

public class PromotionConverter {

    public List<Promotion> convert(List<PromotionEntity> promotionEntities) {
        List<Promotion> promotions = new ArrayList<>();
        for (PromotionEntity promotionEntity : promotionEntities) {
            promotions.add(new Promotion(
                    promotionEntity.name(),
                    promotionEntity.buy(),
                    promotionEntity.get(),
                    promotionEntity.startDate(),
                    promotionEntity.endDate()
            ));
        }
        return promotions;
    }
}
