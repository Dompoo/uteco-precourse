package store.config.controller;

import store.config.infra.repository.RepositoryConfig;
import store.config.io.inputHandler.InputHandlerConfig;
import store.config.io.outputHandler.OutputHandlerConfig;
import store.config.service.dateProvider.DateProviderConfig;
import store.config.service.decisionService.DecisionServiceConfig;
import store.config.service.productService.ProductServiceConfig;
import store.config.service.purchaseService.PurchaseServiceConfig;
import store.controller.Controller;
import store.controller.DefaultController;
import store.controller.RePurchaseControllerProxy;
import store.controller.TransactionControllerProxy;

public class ControllerConfig {

    private final Controller controller;

    public ControllerConfig(
            InputHandlerConfig inputHandlerConfig,
            OutputHandlerConfig outputHandlerConfig,
            DateProviderConfig dateProviderConfig,
            PurchaseServiceConfig purchaseServiceConfig,
            DecisionServiceConfig decisionServiceConfig,
            ProductServiceConfig productServiceConfig,
            RepositoryConfig repositoryConfig
    ) {
        DefaultController defaultController = configDefaultController(inputHandlerConfig, outputHandlerConfig,
                dateProviderConfig, purchaseServiceConfig, decisionServiceConfig, productServiceConfig);
        RePurchaseControllerProxy rePurchaseControllerProxy = configRePurchaseController(inputHandlerConfig,
                defaultController);
        this.controller = configTransactionController(repositoryConfig, rePurchaseControllerProxy);
    }

    private static DefaultController configDefaultController(
            InputHandlerConfig inputHandlerConfig,
            OutputHandlerConfig outputHandlerConfig,
            DateProviderConfig dateProviderConfig,
            PurchaseServiceConfig purchaseServiceConfig,
            DecisionServiceConfig decisionServiceConfig,
            ProductServiceConfig productServiceConfig
    ) {
        return new DefaultController(
                inputHandlerConfig.getInputHandler(),
                outputHandlerConfig.getOutputHandler(),
                dateProviderConfig.getDateProvider(),
                purchaseServiceConfig.getPurchaseService(),
                decisionServiceConfig.getDecisionService(),
                productServiceConfig.getProductService()
        );
    }

    private static RePurchaseControllerProxy configRePurchaseController(
            InputHandlerConfig inputHandlerConfig,
            Controller controller
    ) {
        return new RePurchaseControllerProxy(
                controller,
                inputHandlerConfig.getInputHandler()
        );
    }

    private static TransactionControllerProxy configTransactionController(
            RepositoryConfig repositoryConfig,
            Controller controller
    ) {
        return new TransactionControllerProxy(
                controller,
                repositoryConfig.getRepositories()
        );
    }

    public Controller getController() {
        return this.controller;
    }
}
