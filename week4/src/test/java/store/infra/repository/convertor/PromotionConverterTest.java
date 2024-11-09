package store.infra.repository.convertor;

import java.time.LocalDate;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import store.domain.Promotion;
import store.infra.entity.PromotionEntity;

class PromotionConverterTest {

    private final LocalDate pastDate = LocalDate.now().minusDays(10);
    private final LocalDate futureDate = LocalDate.now().plusDays(10);
    private PromotionConverter sut;

    @BeforeEach
    void setUp() {
        this.sut = new PromotionConverter();
    }

    @Nested
    class 프로모션_변환_테스트 {

        @Test
        void 엔티티를_프로모션으로_변환한다() {
            //given
            List<PromotionEntity> promotions = List.of(
                    new PromotionEntity("콜라1+1", 1, 1, pastDate, futureDate),
                    new PromotionEntity("초코바2+1", 2, 1, pastDate, futureDate),
                    new PromotionEntity("과거프로모션", 1, 1, pastDate, pastDate)
            );

            //when
            List<Promotion> result = sut.convert(promotions);

            //then
            Assertions.assertThat(result).extracting(
                    "name", "buy", "get", "startDate", "endDate"
            ).containsExactlyInAnyOrder(
                    Tuple.tuple("콜라1+1", 1, 1, pastDate, futureDate),
                    Tuple.tuple("초코바2+1", 2, 1, pastDate, futureDate),
                    Tuple.tuple("과거프로모션", 1, 1, pastDate, pastDate)
            );
        }
    }
}
