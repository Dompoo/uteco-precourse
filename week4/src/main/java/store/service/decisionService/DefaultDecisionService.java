package store.service.decisionService;

import java.time.LocalDate;
import java.util.function.Supplier;
import store.domain.Casher;
import store.domain.DecisionType;
import store.domain.Product;
import store.domain.Promotion;
import store.domain.PurchaseType;
import store.domain.membership.Membership;
import store.domain.membership.NoMembership;
import store.domain.membership.RatioMembership;
import store.dto.request.PurchaseRequest;
import store.exception.StoreExceptions;
import store.infra.repository.Repository;

public class DefaultDecisionService implements DecisionService {

    private final Repository<Product> productRepository;

    public DefaultDecisionService(Repository<Product> productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public DecisionType getDecisionType(PurchaseRequest purchaseRequest, LocalDate localDate) {
        Product product = productRepository.findByName(purchaseRequest.productName())
                .orElseThrow(StoreExceptions.PRODUCT_NOT_FOUND::get);

        if (!product.hasPromotion(localDate)) {
            return DecisionType.FULL_DEFAULT;
        }

        Promotion promotion = product.getPromotion();

        return DecisionType.of(
                purchaseRequest.purchaseAmount(),
                product.getDefaultStock(localDate),
                product.getPromotionStock(),
                promotion.getBuy(),
                promotion.getGet(),
                promotion.canPromotion(localDate)
        );
    }

    @Override
    public PurchaseType decidePurchaseType(
            PurchaseRequest purchaseRequest,
            DecisionType decisionType,
            DecisionSupplier<Boolean> bringFreeProductDecisionSupplier,
            DecisionSupplier<Boolean> bringDefaultProductBackDecisionSupplier
    ) {
        Product product = productRepository.findByName(purchaseRequest.productName())
                .orElseThrow(StoreExceptions.PRODUCT_NOT_FOUND::get);

        return Casher.decidePurchaseType(
                product,
                purchaseRequest,
                decisionType,
                bringFreeProductDecisionSupplier,
                bringDefaultProductBackDecisionSupplier
        );
    }

    @Override
    public Membership decideMembership(Supplier<Boolean> membershipDecisionSupplier) {
        if (membershipDecisionSupplier.get()) {
            return new RatioMembership();
        }
        return new NoMembership();
    }
}
