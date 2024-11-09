package store.domain.vo;

import java.util.Objects;

public class Stock {

    private final int defaultStock;
    private final int promotionStock;

    public Stock(int defaultStock, int promotionStock) {
        this.defaultStock = defaultStock;
        this.promotionStock = promotionStock;
    }

    public int getDefaultStock(boolean promotionAvailable) {
        if (promotionAvailable) {
            return defaultStock;
        }
        return defaultStock + promotionStock;
    }

    public int getPromotionStock(boolean promotionAvailable) {
        if (promotionAvailable) {
            return promotionStock;
        }
        return 0;
    }

    public int getTotalStock() {
        return defaultStock + promotionStock;
    }

    public Stock withReducing(int totalDecreaseStock, int promotionDecreaseStock) {
        return new Stock(
                defaultStock - (totalDecreaseStock - promotionDecreaseStock),
                promotionStock - promotionDecreaseStock
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Stock stock)) {
            return false;
        }
        return defaultStock == stock.defaultStock && promotionStock == stock.promotionStock;
    }

    @Override
    public int hashCode() {
        return Objects.hash(defaultStock, promotionStock);
    }
}
