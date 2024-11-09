package store.domain.vo;

public record Stock(int defaultStock, int promotionStock) {

    public int getTotalStock() {
        return defaultStock + promotionStock;
    }

    public Stock withReducing(int totalDecreaseStock, int promotionDecreaseStock) {
        return new Stock(
                defaultStock - (totalDecreaseStock - promotionDecreaseStock),
                promotionStock - promotionDecreaseStock
        );
    }
}
