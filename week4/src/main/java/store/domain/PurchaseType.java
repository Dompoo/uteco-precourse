package store.domain;

import store.domain.vo.PurchaseInfo;
import store.domain.vo.PurchaseStatus;

public enum PurchaseType {

    FULL_DEFAULT(
            PurchaseInfo::purchaseAmount,
            (info) -> 0,
            (info) -> 0
    ),
    FULL_PROMOTION(
            PurchaseInfo::purchaseAmount,
            (info) -> (info.purchaseAmount() / (info.promotionUnit())) * info.promotionGet(),
            PurchaseInfo::purchaseAmount
    ),
    FULL_PROMOTION_BRING_FREE(
            (info) -> ((info.purchaseAmount() / info.promotionUnit()) + 1) * info.promotionUnit(),
            (info) -> ((info.purchaseAmount() / info.promotionUnit()) + 1) * info.promotionGet(),
            (info) -> ((info.purchaseAmount() / info.promotionUnit()) + 1) * info.promotionUnit()
    ),
    FULL_PROMOTION_NOT_BRING_FREE(
            PurchaseInfo::purchaseAmount,
            (info) -> (info.purchaseAmount() / info.promotionUnit()) * info.promotionGet(),
            PurchaseInfo::purchaseAmount
    ),
    PORTION_PROMOTION_BRING_BACK(
            (info) -> {
                if (info.purchaseAmount() > info.promotionStock()) {
                    return (info.promotionStock() / info.promotionUnit()) * info.promotionUnit();
                }
                return (info.purchaseAmount() / info.promotionUnit()) * info.promotionUnit();
            },
            (info) -> {
                if (info.purchaseAmount() > info.promotionStock()) {
                    return (info.promotionStock() / info.promotionUnit()) * info.promotionGet();
                }
                return (info.purchaseAmount() / (info.promotionUnit())) * info.promotionGet();
            },
            (info) -> {
                if (info.purchaseAmount() > info.promotionStock()) {
                    return (info.promotionStock() / info.promotionUnit()) * info.promotionUnit();
                }
                return (info.purchaseAmount() / info.promotionUnit()) * info.promotionUnit();
            }
    ),
    PORTION_PROMOTION_NOT_BRING_BACK(
            PurchaseInfo::purchaseAmount,
            (info) -> {
                if (info.purchaseAmount() > info.promotionStock()) {
                    return (info.promotionStock() / info.promotionUnit()) * info.promotionGet();
                }
                return (info.purchaseAmount() / info.promotionUnit()) * info.promotionGet();
            },
            (info) -> {
                if (info.purchaseAmount() > info.promotionStock()) {
                    return info.promotionStock();
                }
                return info.purchaseAmount();
            }
    ),
    ;

    private final StoreCalculator<Integer> finalPurchaseAmount;
    private final StoreCalculator<Integer> promotionGetAmount;
    private final StoreCalculator<Integer> decreasePromotionStock;

    PurchaseType(
            StoreCalculator<Integer> finalPurchaseAmount,
            StoreCalculator<Integer> promotionGetAmount,
            StoreCalculator<Integer> decreasePromotionStock
    ) {
        this.finalPurchaseAmount = finalPurchaseAmount;
        this.promotionGetAmount = promotionGetAmount;
        this.decreasePromotionStock = decreasePromotionStock;
    }

    public PurchaseStatus purchase(PurchaseInfo purchaseInfo) {
        Integer purchaseAmount = this.finalPurchaseAmount.calculate(purchaseInfo);
        Integer promotionGetAmount = this.promotionGetAmount.calculate(purchaseInfo);
        Integer decreasePromotionStock = this.decreasePromotionStock.calculate(purchaseInfo);
        int promotionedProductAmount = promotionGetAmount / purchaseInfo.promotionGet() * purchaseInfo.promotionUnit();
        return new PurchaseStatus(
                purchaseAmount
,                promotionGetAmount,
                decreasePromotionStock,
                promotionedProductAmount
        );
    }

    @FunctionalInterface
    public interface StoreCalculator<T> {
        T calculate(
                PurchaseInfo purchaseInfo
        );
    }
}


