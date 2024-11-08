package store.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import store.domain.Casher;
import store.domain.DecisionType;
import store.domain.Product;
import store.domain.Promotion;
import store.domain.PurchaseType;
import store.domain.vo.PurchaseInfo;
import store.domain.vo.PurchaseStatus;
import store.dto.request.PurchaseRequest;
import store.dto.response.ProductResponse;
import store.dto.response.PurchaseResult;
import store.infra.repository.Repository;

public class Service {

    private final Repository<Product> productRepository;

    public Service(Repository<Product> productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductResponse> readAllProducts() {
        List<Product> products = productRepository.findAll();

        return ProductResponse.fromList(products);
    }

    public DecisionType getDecisionType(PurchaseRequest purchaseRequest, LocalDate localDate) {
        Product product = productRepository.findByName(purchaseRequest.productName())
                .orElseThrow();

        Promotion promotion = product.getPromotion();

        //TODO : null 가능성
        return DecisionType.of(
                purchaseRequest.count(),
                product.getDefaultStock(),
                product.getPromotionStock(),
                promotion.getBuy(),
                promotion.getGet(),
                promotion.canPromotion(localDate)
        );
    }

    public PurchaseType decidePurchaseType(
            PurchaseRequest purchaseRequest,
            DecisionType decisionType,
            BiPredicate<String, Integer> bringFreeProductPredicate,
            BiPredicate<String, Integer> bringDefaultProductBackPredicate
    ) {
        Product product = productRepository.findByName(purchaseRequest.productName())
                .orElseThrow();

        return Casher.decidePurchaseType(
                product,
                purchaseRequest,
                decisionType,
                bringFreeProductPredicate,
                bringDefaultProductBackPredicate
        );
    }

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
                product.getPrice(),
                purchaseStatus.promotionGetAmount()
        );
    }

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
}
