package store.domain;

import java.time.LocalDate;
import store.domain.validator.ParamsValidator;

final public class Promotion {

    private final String name;
    private final PromotionType promotionType;
    private final LocalDate startDate;
    private final LocalDate endDate;

    public Promotion(String name, PromotionType promotionType, LocalDate startDate, LocalDate endDate) {
        ParamsValidator.validateParamsNotNull(Promotion.class, name, promotionType, startDate, endDate);
        this.name = name;
        this.promotionType = promotionType;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public boolean hasPromotion() {
        return promotionType != PromotionType.NO_PROMOTION;
    }

    public String getName() {
        return name;
    }

    public PromotionType getPromotionType() {
        return promotionType;
    }

    public int getPromotionUnit() {
        return this.promotionType.getPromotionUnit();
    }

    public int getPromotionBuy() {
        return this.promotionType.getBuy();
    }

    public int getPromotionGet() {
        return this.promotionType.getGet();
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }
}
