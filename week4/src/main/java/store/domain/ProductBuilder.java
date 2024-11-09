package store.domain;

public class ProductBuilder {

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
