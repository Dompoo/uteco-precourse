package store.infra.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import store.domain.Product;
import store.domain.vo.Stock;
import store.infra.entity.ProductEntity;
import store.infra.entity.PromotionEntity;
import store.infra.repository.convertor.ProductConverter;
import store.infra.repository.convertor.PromotionConverter;
import store.testUtil.testDouble.ProductFileDatabaseFake;
import store.testUtil.testDouble.PromotionFileDatabaseFake;

class ProductRepositoryTest {

    private ProductRepository sut;

    @BeforeEach
    void setUp() {
        ProductFileDatabaseFake productFileDatabaseFake = new ProductFileDatabaseFake();
        PromotionFileDatabaseFake promotionFileDatabaseFake = new PromotionFileDatabaseFake();
        setUpProductFileDatebaseFake(productFileDatabaseFake);
        setUpPromotionFileDatabaseFake(promotionFileDatabaseFake);

        sut = new ProductRepository(
                productFileDatabaseFake,
                promotionFileDatabaseFake,
                new ProductConverter(),
                new PromotionConverter()
        );
    }

    @Nested
    class 모든_상품_읽기_테스트 {

        @Test
        void 모든_상품을_읽는다() {
            //given

            //when
            List<Product> result = sut.findAll();

            //then
            assertThat(result).extracting(
                    "name", "price", "stock"
            ).containsExactlyInAnyOrder(
                    Tuple.tuple("콜라", 1000, new Stock(5, 5)),
                    Tuple.tuple("감자", 1500, new Stock(10, 0)),
                    Tuple.tuple("땅콩", 1000, new Stock(3, 3)),
                    Tuple.tuple("땅콩버터", 2000, new Stock(0, 5))
            );
        }
    }

    @Nested
    class 이름으로_상품_읽기_테스트 {

        @Test
        void 상품_이름으로_읽는다() {
            //given
            String productName = "콜라";

            //when
            Optional<Product> result = sut.findByName(productName);

            //then
            assertThat(result).isPresent();
            assertThat(result.get()).extracting(
                    "name", "price", "stock"
            ).containsExactly(
                    "콜라", 1000, new Stock(5, 5)
            );
        }

        @Test
        void 존재하지_않는_상품_이름으로_읽는다() {
            //given
            String productName = "없는상품이지롱";

            //when
            Optional<Product> result = sut.findByName(productName);

            //then
            assertThat(result).isEmpty();
        }
    }

    @Nested
    class 상품_업데이트_테스트 {

        @Test
        void 새로운_상품을_업데이트한다() {
            //given
            Product product = new Product("콜라", 1500, 10, 10, null);

            //when
            sut.update(product);

            //then
            assertThat(sut.findByName("콜라").isPresent());
            assertThat(sut.findByName("콜라").get()).extracting(
                    "name", "price", "stock"
            ).containsExactly(
                    "콜라", 1500, new Stock(10, 10)
            );
        }
    }

    private static void setUpProductFileDatebaseFake(ProductFileDatabaseFake productFileDatabaseFake) {
        productFileDatabaseFake.setUpProductEntities(List.of(
                new ProductEntity("콜라", 1000, 5, ""),
                new ProductEntity("콜라", 1000, 5, "콜라1+1"),
                new ProductEntity("감자", 1500, 10, ""),
                new ProductEntity("땅콩", 1000, 3, ""),
                new ProductEntity("땅콩", 1000, 3, "과거프로모션"),
                new ProductEntity("땅콩버터", 2000, 5, "과거프로모션")
        ));
    }

    private static void setUpPromotionFileDatabaseFake(PromotionFileDatabaseFake promotionFileDatabaseFake) {
        LocalDate pastDate = LocalDate.now().minusDays(10);
        LocalDate futureDate = LocalDate.now().plusDays(10);
        promotionFileDatabaseFake.setPromotionEntities(List.of(
                new PromotionEntity("콜라1+1", 1, 1, pastDate, futureDate),
                new PromotionEntity("초코바2+1", 2, 1, pastDate, futureDate),
                new PromotionEntity("과거프로모션", 1, 1, pastDate, pastDate)
        ));
    }
}
