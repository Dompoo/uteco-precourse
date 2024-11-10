package store.service.purchaseService;

import java.util.List;
import java.util.function.Supplier;
import store.common.dto.request.PurchaseRequest;
import store.common.dto.response.PurchaseResult;
import store.common.exception.StoreExceptions;
import store.domain.Product;
import store.domain.Purchase;
import store.domain.PurchaseType;
import store.domain.vo.PurchaseStatus;
import store.infra.repository.Repository;

public class DefaultPurchaseService implements PurchaseService {

    private final Repository<Product> productRepository;

    public DefaultPurchaseService(Repository<Product> productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<PurchaseRequest> getPurchases(Supplier<List<PurchaseRequest>> purchaseRequestsSupplier) {
        List<PurchaseRequest> purchaseRequests = purchaseRequestsSupplier.get();
        validatePurchaseProducts(purchaseRequests);
        return purchaseRequests;
    }

    private void validatePurchaseProducts(List<PurchaseRequest> purchaseRequests) {
        for (PurchaseRequest purchaseRequest : purchaseRequests) {
            Product product = productRepository.findByName(purchaseRequest.productName())
                    .orElseThrow(StoreExceptions.PRODUCT_NOT_FOUND::get);
            if (!product.canPurchase(purchaseRequest.purchaseAmount())) {
                throw StoreExceptions.PURCHASE_OVER_STOCK.get();
            }
        }
    }

    @Override
    public PurchaseResult purchaseProduct(
            PurchaseRequest purchaseRequest,
            PurchaseType purchaseType
    ) {
        Product product = productRepository.findByName(purchaseRequest.productName())
                .orElseThrow(StoreExceptions.PRODUCT_NOT_FOUND::get);
        Purchase purchase = Purchase.of(product, purchaseRequest.purchaseAmount());
        PurchaseStatus purchaseStatus = purchaseType.proceed(purchase);
        product.reduceStock(purchaseStatus.finalPurchaseAmount(), purchaseStatus.decreasePromotionStock());
        productRepository.update(product);
        return PurchaseResult.of(product, purchaseStatus);
    }
}
