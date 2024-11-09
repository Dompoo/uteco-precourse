package store.domain;

import java.time.LocalDate;
import store.domain.validator.ParamsValidator;
import store.exception.StoreExceptions;

final public class Product {

    private final String name;
    private final int price;
    private int defaultStock;
    private int promotionStock;
    private final Promotion promotion;

    public Product(
            String name,
            int price,
            int defaultStock,
            int promotionStock,
            Promotion promotion
    ) {
        ParamsValidator.validateParamsNotNull(Product.class, name);
        validate(price, defaultStock, promotionStock);
        this.name = name;
        this.price = price;
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

    public int calculatePromotionGets(int purchaseAmount) {
        int promotionUnit = this.getPromotion().getPromotionUnit();
        return (((purchaseAmount / promotionUnit) + 1) * promotionUnit) - purchaseAmount;
    }

    public int calculateNoPromotions(int purchaseAmount) {
        int promotionUnit = this.getPromotion().getPromotionUnit();
        if (purchaseAmount < this.getPromotionStock()) {
            return purchaseAmount % promotionUnit;
        }
        return purchaseAmount - (this.getPromotionStock() / promotionUnit) * promotionUnit;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getDefaultStock(LocalDate localDate) {
        if (!hasPromotion(localDate)) {
            return defaultStock + promotionStock;
        }
        return defaultStock;
    }

    public int getPromotionStock() {
        return promotionStock;
    }

    public boolean hasPromotion(LocalDate localDate) {
        return promotion != null && promotion.canPromotion(localDate);
    }

    public Promotion getPromotion() {
        return promotion;
    }

    public static class ProductBuilder {

        private String name;
        private int price;
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
            return new Product(name, price, defaultStock, promotionStock, promotion);
        }
    }
}
