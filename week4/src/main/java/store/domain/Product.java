package store.domain;

import store.common.exception.StoreExceptions;
import store.domain.validator.ParamsValidator;
import store.domain.vo.Stock;

final public class Product {

    private static final int MIN_PRICE = 0;
    private static final int MIN_DEFAULT_STOCK = 0;
    private static final int MIN_PROMOTION_STOCK = 0;

    private final String name;
    private final int price;
    private Stock stock;
    private final Promotion promotion;

    public Product(String name, int price, int defaultStock, int promotionStock, Promotion promotion) {
        ParamsValidator.validateParamsNotNull(Product.class, name, promotion);
        validate(price, defaultStock, promotionStock);
        this.name = name;
        this.price = price;
        this.stock = new Stock(defaultStock, promotionStock);
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
        this.stock = this.stock.withReducing(totalDecreaseStock, promotionDecreaseStock);
    }

    public boolean isJustRightPromotionUnit(int purchaseAmount) {
        if (!hasPromotion()) {
            return false;
        }
        return purchaseAmount % promotion.getPromotionUnit() == 0 && purchaseAmount <= getPromotionStock();
    }

    public boolean canGetFreePromotionProduct(int purchaseAmount) {
        if (!hasPromotion()) {
            return false;
        }
        int promotionUnit = promotion.getPromotionUnit();
        return purchaseAmount % promotionUnit >= promotion.getPromotionBuy()
                && (purchaseAmount / (promotionUnit) + 1) * (promotionUnit) <= getPromotionStock();
    }

    public int calculatePromotionGets(int purchaseAmount) {
        if (!hasPromotion()) {
            return 0;
        }
        int promotionUnit = promotion.getPromotionUnit();
        return (((purchaseAmount / promotionUnit) + 1) * promotionUnit) - purchaseAmount;
    }

    public int calculateNoPromotions(int purchaseAmount) {
        int promotionUnit = promotion.getPromotionUnit();
        if (purchaseAmount < this.getPromotionStock()) {
            return purchaseAmount % promotionUnit;
        }
        return purchaseAmount - (this.getPromotionStock() / promotionUnit) * promotionUnit;
    }

    public boolean hasPromotion() {
        return this.promotion.hasPromotion();
    }

    public boolean canPurchase(int purchaseAmount) {
        return this.stock.getTotalStock() >= purchaseAmount;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getDefaultStock() {
        return stock.defaultStock();
    }

    public int getPromotionStock() {
        return stock.promotionStock();
    }

    public Promotion getPromotion() {
        return promotion;
    }
}
