package store.infra.entity;

import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;
import store.domain.Promotion;

public record PromotionEntity(
        String name,
        int buy,
        int get,
        LocalDate startDate,
        LocalDate endDate
) implements DatabaseEntity {

    public static PromotionEntity from(Map<String, String> dataMap) {
        String name = dataMap.get("name");
        int buy = Integer.parseInt(dataMap.get("buy"));
        int get = Integer.parseInt(dataMap.get("get"));
        LocalDate startDate = LocalDate.parse(dataMap.get("start_date"));
        LocalDate endDate = LocalDate.parse(dataMap.get("end_date"));

        return new PromotionEntity(name, buy, get, startDate, endDate);
    }

    public static PromotionEntity from(Promotion promotion) {
        String name = promotion.getName();
        int buy = promotion.getBuy();
        int get = promotion.getGet();
        LocalDate startDate = promotion.getStartDate();
        LocalDate endDate = promotion.getEndDate();

        return new PromotionEntity(name, buy, get, startDate, endDate);
    }

    @Override
    public String toLine(String[] columns) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String column : columns) {
            if (column.equals("name")) {
                stringBuilder.append(name);
            }
            if (column.equals("buy")) {
                stringBuilder.append(buy);
            }
            if (column.equals("get")) {
                stringBuilder.append(get);
            }
            if (column.equals("startDate")) {
                stringBuilder.append(startDate);
            }
            if (column.equals("endDate")) {
                stringBuilder.append(endDate);
            }
        }
        return stringBuilder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PromotionEntity that)) {
            return false;
        }
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }
}
