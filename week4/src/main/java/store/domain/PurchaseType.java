package store.domain;

public enum PurchaseType {

    FULL_DEFAULT(
            PurchaseInfo::purchaseAmount,
            (info) -> 0,
            (info) -> 0
    ),
    FULL_PROMOTION(
            PurchaseInfo::purchaseAmount,
            (info) -> (info.purchaseAmount() / (info.promotionBuy() + info.promotionGet())) * info.promotionGet(),
            PurchaseInfo::purchaseAmount
    ),
    FULL_PROMOTION_BRING_FREE(
            (info) -> info.purchaseAmount() + info.promotionGet(),
            (info) -> ((info.purchaseAmount() + info.promotionGet()) / (info.promotionBuy() + info.promotionGet())) * info.promotionGet(),
            (info) -> info.purchaseAmount() + info.promotionGet()
    ),
    FULL_PROMOTION_NOT_BRING_FREE(
            PurchaseInfo::purchaseAmount,
            (info) -> ((info.purchaseAmount()) / (info.promotionBuy() + info.promotionGet())) * info.promotionGet(),
            PurchaseInfo::purchaseAmount
    ),
    PORTION_PROMOTION_BRING_BACK(
            (info) -> {
                if (info.purchaseAmount() > info.promotionStock()) {
                    return (info.promotionStock() / (info.promotionBuy() + info.promotionGet())) * (info.promotionGet() + info.promotionBuy());
                }
                return (info.purchaseAmount() / (info.promotionBuy() + info.promotionGet())) * (info.promotionGet() + info.promotionBuy());
            },
            (info) -> {
                if (info.purchaseAmount() > info.promotionStock()) {
                    return (info.promotionStock() / (info.promotionBuy() + info.promotionGet())) * info.promotionGet();
                }
                return (info.purchaseAmount() / (info.promotionBuy() + info.promotionGet())) * info.promotionGet();
            },
            (info) -> {
                if (info.purchaseAmount() > info.promotionStock()) {
                    return (info.promotionStock() / (info.promotionBuy() + info.promotionGet())) * (info.promotionGet() + info.promotionBuy());
                }
                return (info.purchaseAmount() / (info.promotionBuy() + info.promotionGet())) * (info.promotionGet() + info.promotionBuy());
            }
    ),
    PORTION_PROMOTION_NOT_BRING_BACK(
            PurchaseInfo::purchaseAmount,
            (info) -> {
                if (info.purchaseAmount() > info.promotionStock()) {
                    return ((info.promotionStock() - (info.promotionStock() % (info.promotionBuy() + info.promotionGet()))) / (info.promotionBuy() + info.promotionGet())) * info.promotionGet();
                }
                return ((info.purchaseAmount() - (info.purchaseAmount() % (info.promotionBuy() + info.promotionGet()))) / (info.promotionBuy() + info.promotionGet())) * info.promotionGet();
            },
            (info) -> {
                if (info.purchaseAmount() > info.promotionStock()) {
                    return info.promotionStock();
                }
                return info.purchaseAmount();
            }
    ),
    ;

    private final StoreCalculator<Integer> totalPurchase;
    private final StoreCalculator<Integer> promotionGet;
    private final StoreCalculator<Integer> decreasePromotionStock;

    PurchaseType(StoreCalculator<Integer> totalPurchase, StoreCalculator<Integer> promotionGet,
                 StoreCalculator<Integer> decreasePromotionStock) {
        this.totalPurchase = totalPurchase;
        this.promotionGet = promotionGet;
        this.decreasePromotionStock = decreasePromotionStock;
    }

    public PurchaseResult purchase(PurchaseInfo purchaseInfo) {
        return new PurchaseResult(
                this.totalPurchase.calculate(purchaseInfo),
                this.promotionGet.calculate(purchaseInfo),
                this.decreasePromotionStock.calculate(purchaseInfo)
        );
    }

    @FunctionalInterface
    public interface StoreCalculator<T> {
        T calculate(
                PurchaseInfo purchaseInfo
        );
    }

    public record PurchaseInfo(
            int purchaseAmount,
            int promotionStock,
            int promotionBuy,
            int promotionGet
    ) {
    }

    public record PurchaseResult(
            int totalPurchase,
            int promotionGet,
            int decreasePromotionStock
    ) {
    }
}


