package store;

import store.config.aop.RetryHandlerConfig;
import store.config.controller.ControllerConfig;
import store.config.infra.database.ProductDatabaseConfig;
import store.config.infra.database.ProductFileDatabaseConfig;
import store.config.infra.database.PromotionDatabaseConfig;
import store.config.infra.database.PromotionFileDatabaseConfig;
import store.config.infra.repository.DefaultProductRepositoryConfig;
import store.config.infra.repository.ProductRepositoryConfig;
import store.config.infra.repository.RepositoryConfig;
import store.config.io.inputHandler.InputHandlerConfig;
import store.config.io.outputHandler.OutputHandlerConfig;
import store.config.io.reader.MissionUtilsReaderConfig;
import store.config.io.writer.SystemWriterConfig;
import store.config.io.writer.WriterConfig;
import store.config.service.dateProvider.MissionUtilsDateProviderConfig;
import store.config.service.decisionService.DecisionServiceConfig;
import store.config.service.productService.ProductServiceConfig;
import store.config.service.purchaseService.PurchaseServiceConfig;
import store.controller.Controller;

public class StoreApplication {

    private final Controller controller;

    public StoreApplication() {
        WriterConfig writerConfig = new SystemWriterConfig();
        ProductRepositoryConfig productRepositoryConfig = configProductRepository();
        RetryHandlerConfig retryHandlerConfig = new RetryHandlerConfig(writerConfig);
        this.controller = new ControllerConfig(
                new InputHandlerConfig(new MissionUtilsReaderConfig(), writerConfig),
                new OutputHandlerConfig(writerConfig),
                new MissionUtilsDateProviderConfig(),
                new PurchaseServiceConfig(productRepositoryConfig, retryHandlerConfig),
                new DecisionServiceConfig(productRepositoryConfig, retryHandlerConfig),
                new ProductServiceConfig(productRepositoryConfig),
                new RepositoryConfig(productRepositoryConfig),
                retryHandlerConfig
        ).getController();
    }

    private ProductRepositoryConfig configProductRepository() {
        ProductDatabaseConfig productDatabaseConfig = new ProductFileDatabaseConfig();
        PromotionDatabaseConfig promotionDatabaseConfig = new PromotionFileDatabaseConfig();
        return new DefaultProductRepositoryConfig(productDatabaseConfig, promotionDatabaseConfig);
    }

    public void run() {
        this.controller.run();
    }
}
