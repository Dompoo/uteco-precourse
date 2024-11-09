package store.service.purchaseService;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Supplier;
import store.domain.Product;
import store.domain.Purchase;
import store.domain.PurchaseType;
import store.domain.vo.PurchaseStatus;
import store.dto.request.PurchaseRequest;
import store.dto.response.PurchaseResult;
import store.exception.StoreExceptions;
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
            if (!product.isStockSufficient(purchaseRequest.purchaseAmount())) {
                throw StoreExceptions.PURCHASE_OVER_STOCK.get();
            }
        }
    }

    @Override
    public PurchaseResult purchaseProduct(PurchaseRequest purchaseRequest, PurchaseType purchaseType, LocalDate localDate) {
        Product product = productRepository.findByName(purchaseRequest.productName())
                .orElseThrow(StoreExceptions.PRODUCT_NOT_FOUND::get);

        PurchaseStatus purchaseStatus = purchaseType.purchase(createPurchase(purchaseRequest, product, localDate));

        product.reduceStock(purchaseStatus.finalPurchaseAmount(), purchaseStatus.decreasePromotionStock());
        productRepository.update(product);

        return PurchaseResult.of(product, purchaseStatus);
    }

    private static Purchase createPurchase(PurchaseRequest purchaseRequest, Product product, LocalDate localDate) {
        int buy = 1;
        int get = 1;
        if (product.hasPromotion(localDate)) {
            buy = product.getPromotion().getBuy();
            get = product.getPromotion().getGet();
        }
        return new Purchase(purchaseRequest.purchaseAmount(), product.getPromotionStock(), buy, get);
    }
}
