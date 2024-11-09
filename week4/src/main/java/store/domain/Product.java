package store.domain;

import store.domain.validator.ParamsValidator;
import store.domain.vo.Stock;
import store.exception.StoreExceptions;

final public class Product {

    private static final int MIN_PRICE = 0;
    private static final int MIN_DEFAULT_STOCK = 0;
    private static final int MIN_PROMOTION_STOCK = 0;

    private final String name;
    private final int price;
    private Stock stock;
    private final String promotionName;
    private final PromotionType promotionType;

    public Product(
            String name,
            int price,
            int defaultStock,
            int promotionStock,
            String promotionName,
            PromotionType promotionType
    ) {
        ParamsValidator.validateParamsNotNull(Product.class, name, promotionName, promotionType);
        validate(price, defaultStock, promotionStock);
        this.name = name;
        this.price = price;
        this.stock = new Stock(defaultStock, promotionStock);
        this.promotionName = promotionName;
        this.promotionType = promotionType;
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
        return purchaseAmount % promotionType.getPromotionUnit() == 0 && purchaseAmount <= getPromotionStock();
    }

    public boolean canGetFreePromotionProduct(int purchaseAmount) {
        if (!hasPromotion()) {
            return false;
        }
        int promotionUnit = this.promotionType.getPromotionUnit();
        return purchaseAmount % promotionUnit >= this.promotionType.getBuy()
                && (purchaseAmount / (promotionUnit) + 1) * (promotionUnit) <= getPromotionStock();
    }

    public int calculatePromotionGets(int purchaseAmount) {
        int promotionUnit = this.promotionType.getPromotionUnit();
        return (((purchaseAmount / promotionUnit) + 1) * promotionUnit) - purchaseAmount;
    }

    public int calculateNoPromotions(int purchaseAmount) {
        int promotionUnit = this.promotionType.getPromotionUnit();
        if (purchaseAmount < this.getPromotionStock()) {
            return purchaseAmount % promotionUnit;
        }
        return purchaseAmount - (this.getPromotionStock() / promotionUnit) * promotionUnit;
    }

    public boolean hasPromotion() {
        return this.promotionType != PromotionType.NO_PROMOTION;
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
        return stock.getDefaultStock();
    }

    public int getPromotionStock() {
        return stock.getPromotionStock();
    }

    public String getPromotionName() {
        return promotionName;
    }

    public PromotionType getPromotionType() {
        return promotionType;
    }
}
