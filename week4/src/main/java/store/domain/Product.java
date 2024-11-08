package store.domain;

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
        this.name = name;
        this.price = price;
        this.stockType = stockType;
        this.defaultStock = defaultStock;
        this.promotionStock = promotionStock;
        this.promotion = promotion;
    }

    public void reduceStock(int totalDecreaseStock, int promotionDecreaseStock) {
        promotionStock -= promotionDecreaseStock;
        defaultStock -= (totalDecreaseStock - promotionDecreaseStock);
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public StockType getStockType() {
        return stockType;
    }

    public int getDefaultStock() {
        return defaultStock;
    }

    public int getPromotionStock() {
        return promotionStock;
    }

    public Promotion getPromotion() {
        return promotion;
    }

    @Override
    public String toString() {
        String defaultStockString = String.join(",",
                name,
                String.valueOf(price),
                String.valueOf(defaultStock),
                "null"
        );
        if (promotionStock != 0) {
            return stringWithPromotionStock(defaultStockString);
        }
        return defaultStockString;
    }

    private String stringWithPromotionStock(String defaultStockString) {
        String promotionStockString = String.join(",",
                name,
                String.valueOf(price),
                String.valueOf(promotionStock),
                promotion.getName()
        );
        return defaultStockString + "\n" + promotionStockString;
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
