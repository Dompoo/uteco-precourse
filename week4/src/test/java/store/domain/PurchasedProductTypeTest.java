package store.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import store.dto.vo.PurchaseStatus;

class PurchasedProductTypeTest {

    @ParameterizedTest
    @CsvSource({
            "1, 0, 3, 1, 0, 0",
            "2, 0, 3, 2, 0, 0",
            "3, 0, 3, 3, 0, 0",
            "4, 0, 3, 4, 0, 0",
            "1, 0, 1, 1, 0, 0",
            "2, 0, 2, 2, 0, 0",
            "3, 0, 3, 3, 0, 0",
            "4, 0, 4, 4, 0, 0",
    })
    void 전부_기본_재고에서_구매한다(int purchaseAmount, int promotionStock, int promotionBuy,
                         int totalPurchase, int promotionGet, int decreasePromotionStock) {
        //given
        int get = 1;

        Purchase purchase = new Purchase(
                purchaseAmount,
                promotionStock,
                promotionBuy,
                get
        );

        //when
        PurchaseStatus result = PurchaseType.FULL_DEFAULT.purchase(purchase);

        //then
        assertThat(result).extracting(
                "finalPurchaseAmount", "promotionGetAmount", "decreasePromotionStock"
        ).containsExactly(
                totalPurchase, promotionGet, decreasePromotionStock
        );
    }

    @ParameterizedTest
    @CsvSource({
            "4, 13, 3, 4, 1, 4",
            "8, 13, 3, 8, 2, 8",
            "12, 13, 3, 12, 3, 12",
            "3, 13, 2, 3, 1, 3",
            "6, 13, 2, 6, 2, 6",
            "9, 13, 2, 9, 3, 9",
            "12, 13, 2, 12, 4, 12",
            "2, 13, 1, 2, 1, 2",
            "4, 13, 1, 4, 2, 4",
            "6, 13, 1, 6, 3, 6",
            "8, 13, 1, 8, 4, 8",
            "10, 13, 1, 10, 5, 10",
            "12, 13, 1, 12, 6, 12",
    })
    void 전부_프로모션에서_구매한다(int purchaseAmount, int promotionStock, int promotionBuy,
                         int totalPurchase, int promotionGet, int decreasePromotionStock) {
        //given
        int get = 1;

        Purchase purchase = new Purchase(
                purchaseAmount,
                promotionStock,
                promotionBuy,
                get
                );

        //when
        PurchaseStatus result = PurchaseType.FULL_PROMOTION.purchase(purchase);

        //then
        assertThat(result).extracting(
                "finalPurchaseAmount", "promotionGetAmount", "decreasePromotionStock"
        ).containsExactly(
                totalPurchase, promotionGet, decreasePromotionStock
        );
    }

    @ParameterizedTest
    @CsvSource({
            "3, 13, 3, 4, 1, 4",
            "7, 13, 3, 8, 2, 8",
            "11, 13, 3, 12, 3, 12",
            "2, 13, 2, 3, 1, 3",
            "5, 13, 2, 6, 2, 6",
            "8, 13, 2, 9, 3, 9",
            "11, 13, 2, 12, 4, 12",
            "1, 13, 1, 2, 1, 2",
            "3, 13, 1, 4, 2, 4",
            "5, 13, 1, 6, 3, 6",
            "7, 13, 1, 8, 4, 8",
            "9, 13, 1, 10, 5, 10",
            "11, 13, 1, 12, 6, 12",
    })
    void 전부_프로모션에서_구매한다_무료상품을_가져온다(int purchaseAmount, int promotionStock, int promotionBuy,
                        int totalPurchase, int promotionGet, int decreasePromotionStock) {
        //given
        int get = 1;

        Purchase purchase = new Purchase(
                purchaseAmount,
                promotionStock,
                promotionBuy,
                get
        );

        //when
        PurchaseStatus result = PurchaseType.FULL_PROMOTION_BRING_FREE.purchase(purchase);

        //then
        assertThat(result).extracting(
                "finalPurchaseAmount", "promotionGetAmount", "decreasePromotionStock"
        ).containsExactly(
                totalPurchase, promotionGet, decreasePromotionStock
        );
    }

    @ParameterizedTest
    @CsvSource({
            "3, 13, 3, 3, 0, 3",
            "7, 13, 3, 7, 1, 7",
            "11, 13, 3, 11, 2, 11",
            "2, 13, 2, 2, 0, 2",
            "5, 13, 2, 5, 1, 5",
            "8, 13, 2, 8, 2, 8",
            "11, 13, 2, 11, 3, 11",
            "1, 13, 1, 1, 0, 1",
            "3, 13, 1, 3, 1, 3",
            "5, 13, 1, 5, 2, 5",
            "7, 13, 1, 7, 3, 7",
            "9, 13, 1, 9, 4, 9",
            "11, 13, 1, 11, 5, 11",
    })
    void 전부_프로모션에서_구매한다_무료상품을_가져오지_않는다(int purchaseAmount, int promotionStock, int promotionBuy,
                                   int totalPurchase, int promotionGet, int decreasePromotionStock) {
        //given
        int get = 1;

        Purchase purchase = new Purchase(
                purchaseAmount,
                promotionStock,
                promotionBuy,
                get
        );

        //when
        PurchaseStatus result = PurchaseType.FULL_PROMOTION_NOT_BRING_FREE.purchase(purchase);

        //then
        assertThat(result).extracting(
                "finalPurchaseAmount", "promotionGetAmount", "decreasePromotionStock"
        ).containsExactly(
                totalPurchase, promotionGet, decreasePromotionStock
        );
    }

    @ParameterizedTest
    @CsvSource({
            "1, 13, 3, 0, 0, 0",
            "2, 13, 3, 0, 0, 0",
            "5, 13, 3, 4, 1, 4",
            "6, 13, 3, 4, 1, 4",
            "9, 13, 3, 8, 2, 8",
            "10, 13, 3, 8, 2, 8",
            "13, 13, 3, 12, 3, 12",
            "14, 13, 3, 12, 3, 12",
            "15, 13, 3, 12, 3, 12",
            "1, 13, 2, 0, 0, 0",
            "4, 13, 2, 3, 1, 3",
            "7, 13, 2, 6, 2, 6",
            "10, 13, 2, 9, 3, 9",
            "13, 13, 2, 12, 4, 12",
            "14, 13, 2, 12, 4, 12",
            "15, 13, 2, 12, 4, 12",
            "16, 13, 2, 12, 4, 12",
            "13, 13, 1, 12, 6, 12",
            "14, 13, 1, 12, 6, 12",
            "15, 13, 1, 12, 6, 12",
            "16, 13, 1, 12, 6, 12",
    })
    void 일부만_프로모션에서_구매한다_프로모션_아닌_것은_가져다_놓는다(int purchaseAmount, int promotionStock, int promotionBuy,
                                       int totalPurchase, int promotionGet, int decreasePromotionStock) {
        //given
        int get = 1;

        Purchase purchase = new Purchase(
                purchaseAmount,
                promotionStock,
                promotionBuy,
                get
        );

        //when
        PurchaseStatus result = PurchaseType.PORTION_PROMOTION_BRING_BACK.purchase(purchase);

        //then
        assertThat(result).extracting(
                "finalPurchaseAmount", "promotionGetAmount", "decreasePromotionStock"
        ).containsExactly(
                totalPurchase, promotionGet, decreasePromotionStock
        );
    }

    @ParameterizedTest
    @CsvSource({
            "1, 13, 3, 1, 0, 1",
            "2, 13, 3, 2, 0, 2",
            "5, 13, 3, 5, 1, 5",
            "6, 13, 3, 6, 1, 6",
            "9, 13, 3, 9, 2, 9",
            "10, 13, 3, 10, 2, 10",
            "13, 13, 3, 13, 3, 13",
            "14, 13, 3, 14, 3, 13",
            "15, 13, 3, 15, 3, 13",
            "1, 13, 2, 1, 0, 1",
            "4, 13, 2, 4, 1, 4",
            "7, 13, 2, 7, 2, 7",
            "10, 13, 2, 10, 3, 10",
            "13, 13, 2, 13, 4, 13",
            "14, 13, 2, 14, 4, 13",
            "15, 13, 2, 15, 4, 13",
            "16, 13, 2, 16, 4, 13",
            "13, 13, 1, 13, 6, 13",
            "14, 13, 1, 14, 6, 13",
            "15, 13, 1, 15, 6, 13",
            "16, 13, 1, 16, 6, 13",
    })
    void 일부만_프로모션에서_구매한다_프로모션_아닌_것도_그대로_구매한다(int purchaseAmount, int promotionStock, int promotionBuy,
                                            int totalPurchase, int promotionGet, int decreasePromotionStock) {
        //given
        int get = 1;

        Purchase purchase = new Purchase(
                purchaseAmount,
                promotionStock,
                promotionBuy,
                get
        );

        //when
        PurchaseStatus result = PurchaseType.PORTION_PROMOTION_NOT_BRING_BACK.purchase(purchase);

        //then
        assertThat(result).extracting(
                "finalPurchaseAmount", "promotionGetAmount", "decreasePromotionStock"
        ).containsExactly(
                totalPurchase, promotionGet, decreasePromotionStock
        );
    }

}