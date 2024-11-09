package store.config.service.purchaseService;

import store.config.aop.RetryHandlerConfig;
import store.config.infra.repository.ProductRepositoryConfig;
import store.service.purchaseService.DefaultPurchaseService;
import store.service.purchaseService.PurchaseService;
import store.service.purchaseService.PurchaseServiceRetryProxy;

public class PurchaseServiceConfig {

    private final PurchaseService purchaseService;

    public PurchaseServiceConfig(
            ProductRepositoryConfig productRepositoryConfig,
            RetryHandlerConfig retryHandlerConfig
    ) {
        DefaultPurchaseService defaultPurchaseService = new DefaultPurchaseService(
                productRepositoryConfig.getProductRepository()
        );
        this.purchaseService = new PurchaseServiceRetryProxy(
                defaultPurchaseService,
                retryHandlerConfig.getRetryHandler()
        );
    }

    public PurchaseService getPurchaseService() {
        return this.purchaseService;
    }
}
