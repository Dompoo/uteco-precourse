package store.infra.entity;

import java.util.Map;

public record ProductEntity(
        String name,
        int price,
        int stock,
        String promotionName
) implements DatabaseEntity {

    public static ProductEntity from(Map<String, String> dataMap) {
        String name = dataMap.get("name");
        int price = Integer.parseInt(dataMap.get("price"));
        int stock = Integer.parseInt(dataMap.get("stock"));
        String promotionName = dataMap.get("promotionName");

        return new ProductEntity(name, price, stock, promotionName);
    }

    @Override
    public String toLine(String[] columns) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String column : columns) {
            if (column.equals("name")) {
                stringBuilder.append(name);
            }
            if (column.equals("price")) {
                stringBuilder.append(price);
            }
            if (column.equals("stock")) {
                stringBuilder.append(stock);
            }
            if (column.equals("promotionName")) {
                stringBuilder.append(promotionName);
            }
        }
        return stringBuilder.toString();
    }
}
