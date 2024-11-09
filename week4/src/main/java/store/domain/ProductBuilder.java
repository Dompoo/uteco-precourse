package store.domain;

public class ProductBuilder {

    private String name;
    private int price;
    private int defaultStock;
    private int promotionStock;
    private String promotionName;
    private PromotionType promotionType;

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

    public ProductBuilder setPromotionName(String promotionName) {
        this.promotionName = promotionName;
        return this;
    }

    public ProductBuilder setPromotionType(PromotionType promotionType) {
        this.promotionType = promotionType;
        return this;
    }

    public Product build() {
        return new Product(name, price, defaultStock, promotionStock, promotionName, promotionType);
    }
}
