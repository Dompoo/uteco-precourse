package store.domain;

import java.time.LocalDate;
import store.domain.validator.ParamsValidator;
import store.exception.StoreExceptions;

final public class Promotion {

    private final String name;
    private final int buy;
    private final int get;
    private final LocalDate startDate;
    private final LocalDate endDate;

    public Promotion(String name, int buy, int get, LocalDate startDate, LocalDate endDate) {
        ParamsValidator.validateParamsNotNull(Promotion.class, name, startDate, endDate);
        validate(name, buy, get);
        this.name = name;
        this.buy = buy;
        this.get = get;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    private static void validate(String name, int buy, int get) {
        if (name.isBlank()) {
            throw StoreExceptions.ILLEGAL_ARGUMENT.get();
        }
        if (buy <= 0) {
            throw StoreExceptions.ILLEGAL_ARGUMENT.get();
        }
        if (get <= 0) {
            throw StoreExceptions.ILLEGAL_ARGUMENT.get();
        }
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
}
