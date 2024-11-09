package store.infra.entity;

import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;

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

    public boolean isAvailable(LocalDate localDate) {
        return startDate.isBefore(localDate) && localDate.isBefore(endDate);
    }

    @Override
    public String toLine(String[] columns) {
        StringJoiner stringJoiner = new StringJoiner(",");
        for (String column : columns) {
            if (column.equals("name")) {
                stringJoiner.add(name);
            }
            if (column.equals("buy")) {
                stringJoiner.add(String.valueOf(buy));
            }
            if (column.equals("get")) {
                stringJoiner.add(String.valueOf(get));
            }
            if (column.equals("start_date")) {
                stringJoiner.add(String.valueOf(startDate));
            }
            if (column.equals("end_date")) {
                stringJoiner.add(String.valueOf(endDate));
            }
        }
        return stringJoiner.toString();
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
