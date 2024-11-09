package store.service.decisionService;

import java.time.LocalDate;
import java.util.function.Supplier;
import store.domain.Casher;
import store.domain.DecisionType;
import store.domain.Product;
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
        return DecisionType.of(product, purchaseRequest.purchaseAmount(), localDate);
    }

    @Override
    public PurchaseType decidePurchaseType(
            PurchaseRequest purchaseRequest,
            DecisionType decisionType,
            LocalDate localDate,
            DecisionSupplier<Boolean> bringFreeProductDecisionSupplier,
            DecisionSupplier<Boolean> bringDefaultProductBackDecisionSupplier
    ) {
        Product product = productRepository.findByName(purchaseRequest.productName())
                .orElseThrow(StoreExceptions.PRODUCT_NOT_FOUND::get);

        return Casher.decidePurchaseType(
                product,
                purchaseRequest.purchaseAmount(),
                decisionType,
                localDate,
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
