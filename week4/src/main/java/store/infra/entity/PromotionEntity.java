package store.infra.entity;

import java.time.LocalDate;
import java.util.Map;

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
        LocalDate startDate = LocalDate.parse(dataMap.get("startDate"));
        LocalDate endDate = LocalDate.parse(dataMap.get("endDate"));

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
}
