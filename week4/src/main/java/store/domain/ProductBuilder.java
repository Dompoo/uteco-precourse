package store.domain;

import java.time.LocalDate;
import store.infra.entity.PromotionEntity;

public class ProductBuilder {

    private String name;
    private int price;
    private int defaultStock;
    private int promotionStock;
    private Promotion promotion;

    public ProductBuilder(LocalDate now) {
        this.promotion = new Promotion(
                "",
                PromotionType.NO_PROMOTION,
                now.minusDays(10),
                now.minusDays(10)
        );
    }

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

    public ProductBuilder setPromotion(PromotionEntity promotionEntity) {
        this.promotion = new Promotion(
                promotionEntity.name(),
                PromotionType.of(promotionEntity.buy(), promotionEntity.get()),
                promotionEntity.startDate(),
                promotionEntity.endDate()
        );
        return this;
    }

    public Product build() {
        return new Product(name, price, defaultStock, promotionStock, promotion);
    }
}
