package store.io.output;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import store.domain.Product;
import store.domain.Promotion;
import store.dto.response.ProductResponse;
import store.dto.response.PromotionedProductResponse;
import store.dto.response.PurchaseCostResponse;
import store.dto.response.PurchasedProductResponse;
import store.testUtil.testDouble.WriterFake;

class OutputHandlerTest {

    private final LocalDate pastDate = LocalDate.now().minusDays(10);
    private final LocalDate futureDate = LocalDate.now().plusDays(10);
    private final LocalDate now = LocalDate.now();
    private WriterFake writerFake;
    private OutputHandler sut;

    @BeforeEach
    void setUp() {
        this.writerFake = new WriterFake();
        this.sut = new OutputHandler(writerFake, new OutputParser());
    }

    @Nested
    class 인삿말_출력_테스트 {

        @Test
        void 인삿말을_출력한다() {
            //given

            //when
            sut.handleGreetings();

            //then
            assertThat(writerFake.getOutput()).contains(
                            "안녕하세요. W편의점입니다."
                    );
        }
    }

    @Nested
    class 상품_출력_테스트 {

        @Test
        void 상품들을_출력한다() {
            //given
            Promotion promotion1 = new Promotion("콜라1+1", 1, 1, pastDate, futureDate);
            Promotion promotion2 = new Promotion("초코바1+1", 1, 1, pastDate, futureDate);

            List<ProductResponse> productResponses = ProductResponse.fromList(List.of(
                            new Product("콜라", 1500, 0, 10, promotion1),
                            new Product("초코바", 2000, 50, 15, promotion2),
                            new Product("감자", 500, 75, 0, null))
                    , now
            );

            //when
            sut.handleProducts(productResponses);

            //then
            assertThat(writerFake.getOutput()).contains(
                    "현재 보유하고 있는 상품입니다.",
                    "- 감자 500원 75개",
                    "- 초코바 2,000원 15개 초코바1+1",
                    "- 초코바 2,000원 50개",
                    "- 콜라 1,500원 재고 없음",
                    "- 콜라 1,500원 10개 콜라1+1"
            );
        }

        @Test
        void 기본_재고만_있는_상품이_재고가_바닥나면_재고없음으로_처리된다() {
            //given
            List<ProductResponse> productResponses = ProductResponse.fromList(List.of(
                            new Product("감자", 500, 0, 0, null))
                    , now
            );

            //when
            sut.handleProducts(productResponses);

            //then
            assertThat(writerFake.getOutput()).contains(
                    "- 감자 500원 재고 없음"
            );
        }

        @Test
        void 프로모션_재고만_있는_상품이_재고가_바닥나면_재고없음으로_처리된다() {
            //given
            Promotion promotion = new Promotion("콜라1+1", 1, 1, pastDate, futureDate);

            List<ProductResponse> productResponses = ProductResponse.fromList(List.of(
                            new Product("콜라", 1500, 0, 0, promotion))
                    , now
            );

            //when
            sut.handleProducts(productResponses);

            //then
            assertThat(writerFake.getOutput()).contains(
                    "- 콜라 1,500원 재고 없음",
                    "- 콜라 1,500원 재고 없음 콜라1+1"
            );
        }

        @Test
        void 두_재고가_모두_있는_상품의_기본_재고가_바닥나면_재고없음으로_처리된다() {
            //given
            Promotion promotion = new Promotion("초코바1+1", 1, 1, pastDate, futureDate);

            List<ProductResponse> productResponses = ProductResponse.fromList(List.of(
                            new Product("초코바", 2000, 0, 15, promotion))
                    , now
            );

            //when
            sut.handleProducts(productResponses);

            //then
            assertThat(writerFake.getOutput()).contains(
                    "- 초코바 2,000원 재고 없음",
                    "- 초코바 2,000원 15개 초코바1+1"
            );
        }

        @Test
        void 두_재고가_모두_있는_상품의_프로모션_재고가_바닥나면_재고없음으로_처리된다() {
            //given
            Promotion promotion = new Promotion("초코바1+1", 1, 1, pastDate, futureDate);

            List<ProductResponse> productResponses = ProductResponse.fromList(List.of(
                            new Product("초코바", 2000, 50, 0, promotion))
                    , now
            );

            //when
            sut.handleProducts(productResponses);

            //then
            assertThat(writerFake.getOutput()).contains(
                    "- 초코바 2,000원 재고 없음 초코바1+1",
                    "- 초코바 2,000원 50개"
            );
        }
    }

    @Nested
    class 구매한_상품_출력_테스트 {

        @Test
        void 구매한_상품들을_출력한다() {
            //given
            List<PurchasedProductResponse> purchasedProducts = List.of(
                    new PurchasedProductResponse("콜라", 10, 1000),
                    new PurchasedProductResponse("초코바", 3, 1500),
                    new PurchasedProductResponse("감자", 4, 500)
            );

            //when
            sut.handlePurchasedProcuts(purchasedProducts);

            //then
            assertThat(getOutputWithOneSpace()).contains(
                    "==============W 편의점================",
                    "상품명 수량 금액",
                    "콜라 10 10,000",
                    "초코바 3 4,500",
                    "감자 4 2,000"
            );
        }

        @Test
        void 구매를_중간에_포기하여_0개_구매한_상품도_출력한다() {
            //given
            List<PurchasedProductResponse> purchasedProducts = List.of(
                    new PurchasedProductResponse("콜라", 0, 1000)
            );

            //when
            sut.handlePurchasedProcuts(purchasedProducts);

            //then
            assertThat(getOutputWithOneSpace()).contains(
                    "콜라 0 0"
            );
        }
    }

    @Nested
    class 프로모션된_무료_상품_출력_테스트 {

        @Test
        void 프로모션된_무료_상품들을_출력한다() {
            //given
            List<PromotionedProductResponse> promotionedProductResponses = List.of(
                    new PromotionedProductResponse("콜라", 1),
                    new PromotionedProductResponse("감자", 5)
            );

            //when
            sut.handlePromotionedProducts(promotionedProductResponses);

            //then
            assertThat(getOutputWithOneSpace()).contains(
                    "콜라 1",
                    "감자 5"
            );
        }
    }

    @Nested
    class 최종_계산_출력_테스트 {

        @Test
        void 최종_계산_내역을_출력한다() {
            //given
            PurchaseCostResponse purchaseCostResponse = new PurchaseCostResponse(10000, 5, 1000, 300, 8700);

            //when
            sut.handlePurchaseCost(purchaseCostResponse);

            //then
            assertThat(getOutputWithOneSpace()).contains(
                    "====================================",
                    "총구매액 5 10,000",
                    "행사할인 -1,000",
                    "멤버십할인 -300",
                    "내실돈 8,700"
            );
        }

    }

    private String getOutputWithOneSpace() {
        return writerFake.getOutput().replaceAll("\\s+", " ");
    }
}