package store.domain;

import java.time.LocalDate;
import store.domain.validator.ParamsValidator;
import store.exception.StoreExceptions;

final public class Product {

    private static final int MIN_PRICE = 0;
    private static final int MIN_DEFAULT_STOCK = 0;
    private static final int MIN_PROMOTION_STOCK = 0;

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
        if (price < MIN_PRICE) {
            throw StoreExceptions.ILLEGAL_ARGUMENT.get();
        }
        if (defaultStock < MIN_DEFAULT_STOCK) {
            throw StoreExceptions.ILLEGAL_ARGUMENT.get();
        }
        if (promotionStock < MIN_PROMOTION_STOCK) {
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

    public int calculateNoPromotions(int purchaseAmount, LocalDate localDate) {
        int promotionUnit = this.getPromotion().getPromotionUnit();
        if (purchaseAmount < this.getPromotionStock(localDate)) {
            return purchaseAmount % promotionUnit;
        }
        return purchaseAmount - (this.getPromotionStock(localDate) / promotionUnit) * promotionUnit;
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

    public int getPromotionStock(LocalDate localDate) {
        if (!hasPromotion(localDate)) {
            return 0;
        }
        return promotionStock;
    }

    public boolean hasPromotion(LocalDate localDate) {
        return promotion != null && promotion.canPromotion(localDate);
    }

    public Promotion getPromotion() {
        return promotion;
    }
}
