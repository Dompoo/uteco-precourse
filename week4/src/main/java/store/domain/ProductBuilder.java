package store.domain;

import java.time.LocalDate;
import store.infra.entity.PromotionEntity;

public class ProductBuilder {

    private String name;
    private int price;
    private int defaultStock;
    private int promotionStock;
    private Promotion promotion;

    public ProductBuilder(final LocalDate now) {
        this.promotion = new Promotion(
                "",
                PromotionType.NO_PROMOTION,
                now.minusDays(10),
                now.minusDays(10)
        );
    }

    public ProductBuilder setName(final String name) {
        this.name = name;
        return this;
    }

    public ProductBuilder setPrice(final int price) {
        this.price = price;
        return this;
    }

    public ProductBuilder setDefaultStock(final int defaultStock) {
        this.defaultStock = defaultStock;
        return this;
    }

    public ProductBuilder setPromotionStock(final int promotionStock) {
        this.promotionStock = promotionStock;
        return this;
    }

    public ProductBuilder setPromotion(final PromotionEntity promotionEntity) {
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
