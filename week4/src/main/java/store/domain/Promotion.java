package store.domain;

import java.time.LocalDate;
import store.domain.validator.ParamsValidator;

final public class Promotion {

    private final String name;
    private final int buy;
    private final int get;
    private final LocalDate startDate;
    private final LocalDate endDate;

    public Promotion(String name, int buy, int get, LocalDate startDate, LocalDate endDate) {
        ParamsValidator.validateParamsNotNull(Promotion.class, name, startDate, endDate);
        this.name = name;
        this.buy = buy;
        this.get = get;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public boolean canPromotion(LocalDate localDate) {
        return startDate.isBefore(localDate) && localDate.isBefore(endDate);
    }

    public String getName() {
        return name;
    }

    public int getBuy() {
        return buy;
    }

    public int getGet() {
        return get;
    }

    public int getPromotionUnit() {
        return buy + get;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    @Override
    public String toString() {
        return String.join(",",
                name,
                String.valueOf(buy),
                String.valueOf(get),
                String.valueOf(startDate),
                String.valueOf(endDate)
        );
    }
}
