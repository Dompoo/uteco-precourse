package store.domain;

public enum StockType {

    DEFAULT_ONLY,
    PROMOTION_ONLY,
    DEFAULT_AND_PROMOTION,
    ;

    public StockType update(StockType stockType) {
        if (this != stockType) {
            return DEFAULT_AND_PROMOTION;
        }
        return stockType;
    }
}
