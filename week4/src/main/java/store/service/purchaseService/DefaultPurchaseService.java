package store.service.purchaseService;

import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import store.domain.Product;
import store.domain.PurchaseType;
import store.domain.vo.PurchaseInfo;
import store.domain.vo.PurchaseStatus;
import store.dto.request.PurchaseRequest;
import store.dto.response.PurchaseResult;
import store.infra.repository.Repository;

public class DefaultPurchaseService implements PurchaseService {

    private final Repository<Product> productRepository;

    public DefaultPurchaseService(Repository<Product> productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<PurchaseRequest> getPurchases(Supplier<List<PurchaseRequest>> purchaseRequestsSupplier) {
        List<PurchaseRequest> purchaseRequests = purchaseRequestsSupplier.get();
        if (!isAllPurchaseProductValid(purchaseRequests)) {
            throw new IllegalArgumentException("zz");
        }
        return purchaseRequests;
    }

    private boolean isAllPurchaseProductValid(List<PurchaseRequest> purchaseRequests) {
        Set<String> validNames = productRepository.findAll().stream()
                .map(Product::getName)
                .collect(Collectors.toSet());

        Set<String> requestNames = purchaseRequests.stream()
                .map(PurchaseRequest::productName)
                .collect(Collectors.toSet());

        return validNames.containsAll(requestNames);
    }

    @Override
    public PurchaseResult purchaseProduct(PurchaseRequest purchaseRequest, PurchaseType purchaseType) {
        Product product = productRepository.findByName(purchaseRequest.productName())
                .orElseThrow();

        PurchaseStatus purchaseStatus = purchaseType.purchase(new PurchaseInfo(
                purchaseRequest.count(),
                product.getPromotionStock(),
                product.getPromotion().getBuy(),
                product.getPromotion().getGet()
        ));

        product.reduceStock(purchaseStatus.finalPurchaseAmount(), purchaseStatus.decreasePromotionStock());
        productRepository.update(product);

        return new PurchaseResult(
                product.getName(),
                purchaseStatus.finalPurchaseAmount(),
                purchaseStatus.promotionedProductAmount(),
                product.getPrice(),
                purchaseStatus.promotionGetAmount()
        );
    }
}
