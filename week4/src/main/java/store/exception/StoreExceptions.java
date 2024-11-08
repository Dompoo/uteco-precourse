package store.exception;

public enum StoreExceptions {

    INVALID_PURCHASE_FORMAT(
            "올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.",
            IllegalArgumentException.class
    ),
    PRODUCT_NOT_FOUND(
            "존재하지 않는 상품입니다. 다시 입력해 주세요.",
            IllegalArgumentException.class
    ),
    PURCHASE_OVER_STOCK(
            "재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.",
            IllegalArgumentException.class
    ),
    ILLEGAL_ARGUMENT(
            "잘못된 입력입니다. 다시 입력해 주세요.",
            IllegalArgumentException.class
    ),
    ;

    private final String message;
    private final Class<? extends RuntimeException> exceptionType;

    StoreExceptions(String message, Class<? extends RuntimeException> exceptionType) {
        this.message = message;
        this.exceptionType = exceptionType;
    }

    public RuntimeException get() {
        try {
            return exceptionType.getDeclaredConstructor(String.class).newInstance(message);
        } catch (Exception e) {
            return new RuntimeException(message);
        }
    }
}
