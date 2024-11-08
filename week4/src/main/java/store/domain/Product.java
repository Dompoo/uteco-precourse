package store.domain;

import store.domain.validator.ParamsValidator;
import store.exception.StoreExceptions;

final public class Product {

    private final String name;
    private final int price;
    private final StockType stockType;
    private int defaultStock;
    private int promotionStock;
    private final Promotion promotion;

    public Product(
            String name,
            int price,
            StockType stockType,
            int defaultStock,
            int promotionStock,
            Promotion promotion
    ) {
        ParamsValidator.validateParamsNotNull(Product.class, name, stockType);
        validate(price, defaultStock, promotionStock);
        this.name = name;
        this.price = price;
        this.stockType = stockType;
        this.defaultStock = defaultStock;
        this.promotionStock = promotionStock;
        this.promotion = promotion;
    }

    private static void validate(int price, int defaultStock, int promotionStock) {
        if (price < 0) {
            throw StoreExceptions.ILLEGAL_ARGUMENT.get();
        }
        if (defaultStock < 0) {
            throw StoreExceptions.ILLEGAL_ARGUMENT.get();
        }
        if (promotionStock < 0) {
            throw StoreExceptions.ILLEGAL_ARGUMENT.get();
        }
    }

    public void reduceStock(int totalDecreaseStock, int promotionDecreaseStock) {
        promotionStock -= promotionDecreaseStock;
        defaultStock -= (totalDecreaseStock - promotionDecreaseStock);
    }

    public boolean isStockSufficient(int count) {
        return count <= defaultStock + promotionStock;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public boolean hasPromotionStock() {
        return stockType == StockType.PROMOTION_ONLY || stockType == StockType.DEFAULT_AND_PROMOTION;
    }

    public int getDefaultStock() {
        return defaultStock;
    }

    public int getPromotionStock() {
        return promotionStock;
    }

    public boolean hasPromotion() {
        return promotion != null;
    }

    public Promotion getPromotion() {
        return promotion;
    }

    public static class ProductBuilder {

        private String name;
        private int price;
        private StockType stockType;
        private int defaultStock;
        private int promotionStock;
        private Promotion promotion;

        public ProductBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public ProductBuilder setPrice(int price) {
            this.price = price;
            return this;
        }

        public StockType getStockType() {
            return stockType;
        }

        public ProductBuilder setStockType(StockType stockType) {
            this.stockType = stockType;
            return this;
        }

        public ProductBuilder setDefaultStock(int defaultStock) {
            this.defaultStock = defaultStock;
            return this;
        }

        public ProductBuilder setPromotionStock(int promotionStock) {
            this.promotionStock = promotionStock;
            return this;
        }

        public ProductBuilder setPromotion(Promotion promotion) {
            this.promotion = promotion;
            return this;
        }

        public Product build() {
            return new Product(name, price, stockType, defaultStock, promotionStock, promotion);
        }
    }
}
