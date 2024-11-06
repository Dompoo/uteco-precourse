package store.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class DecisionTypeTest {

    @ParameterizedTest
    @CsvSource({
            "1, 0, 3",
            "2, 0, 3",
            "3, 0, 3",
            "4, 0, 3",
            "1, 0, 1",
            "2, 0, 2",
            "3, 0, 3",
            "4, 0, 4",
    })
    void 프로모션_재고가_없으면_그냥_구매한다(int purchaseAmount, int promotionStock, int promotionBuy) {
        //given
        int defaultStock = 100;
        int promotionGet = 1;

        //when
        DecisionType result = DecisionType.getDecisionType(
                purchaseAmount,
                defaultStock,
                promotionStock,
                promotionBuy,
                promotionGet
        );

        //then
        assertThat(result).isEqualTo(DecisionType.FULL_DEFAULT);
    }

    @ParameterizedTest
    @CsvSource({
            "4, 13, 3",
            "8, 13, 3",
            "12, 13, 3",
            "3, 13, 2",
            "6, 13, 2",
            "9, 13, 2",
            "12, 13, 2",
            "2, 13, 1",
            "4, 13, 1",
            "6, 13, 1",
            "8, 13, 1",
            "10, 13, 1",
            "12, 13, 1",
    })
    void 프로모션_단위에_딱_맞게_구매한다(int purchaseAmount, int promotionStock, int promotionBuy) {
        //given
        int defaultStock = 100;
        int promotionGet = 1;

        //when
        DecisionType result = DecisionType.getDecisionType(
                purchaseAmount,
                defaultStock,
                promotionStock,
                promotionBuy,
                promotionGet
        );

        //then
        assertThat(result).isEqualTo(DecisionType.FULL_PROMOTION);
    }

    @ParameterizedTest
    @CsvSource({
            "3, 13, 3",
            "7, 13, 3",
            "11, 13, 3",
            "2, 13, 2",
            "5, 13, 2",
            "8, 13, 2",
            "11, 13, 2",
            "1, 13, 1",
            "3, 13, 1",
            "5, 13, 1",
            "7, 13, 1",
            "9, 13, 1",
            "11, 13, 1",
    })
    void 무료로_프로모션_가져갈_수_있게_구매한다(int purchaseAmount, int promotionStock, int promotionBuy) {
        //given
        int defaultStock = 100;
        int promotionGet = 1;

        //when
        DecisionType result = DecisionType.getDecisionType(
                purchaseAmount,
                defaultStock,
                promotionStock,
                promotionBuy,
                promotionGet
        );

        //then
        assertThat(result).isEqualTo(DecisionType.CAN_GET_FREE_PRODUCT);
    }

    @ParameterizedTest
    @CsvSource({
            "1, 13, 3",
            "2, 13, 3",
            "5, 13, 3",
            "6, 13, 3",
            "9, 13, 3",
            "10, 13, 3",
            "13, 13, 3",
            "14, 13, 3",
            "15, 13, 3",
            "1, 13, 2",
            "4, 13, 2",
            "7, 13, 2",
            "10, 13, 2",
            "13, 13, 2",
            "14, 13, 2",
            "15, 13, 2",
            "16, 13, 2",
            "13, 13, 1",
            "14, 13, 1",
            "15, 13, 1",
            "16, 13, 1",
    })
    void 일부는_정가로_구매해야_하도록_구매한다(int purchaseAmount, int promotionStock, int promotionBuy) {
        //given
        int defaultStock = 100;
        int promotionGet = 1;

        //when
        DecisionType result = DecisionType.getDecisionType(
                purchaseAmount,
                defaultStock,
                promotionStock,
                promotionBuy,
                promotionGet
        );

        //then
        assertThat(result).isEqualTo(DecisionType.PROMOTION_STOCK_LACK);
    }

}