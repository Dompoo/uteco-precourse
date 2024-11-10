package store.config.controller;

import store.config.aop.RetryHandlerConfig;
import store.config.infra.repository.RepositoriesConfig;
import store.config.io.inputHandler.InputHandlerConfig;
import store.config.io.outputHandler.OutputHandlerConfig;
import store.config.service.dateProvider.DateProviderConfig;
import store.config.service.decisionService.DecisionServiceConfig;
import store.config.service.productService.ProductServiceConfig;
import store.config.service.purchaseService.PurchaseServiceConfig;
import store.controller.Controller;
import store.controller.ControllerRePurchaseProxy;
import store.controller.ControllerTransactionProxy;
import store.controller.DefaultController;

public class ControllerConfig {

    private final Controller controller;

    public ControllerConfig(
            InputHandlerConfig inputHandlerConfig,
            OutputHandlerConfig outputHandlerConfig,
            DateProviderConfig dateProviderConfig,
            PurchaseServiceConfig purchaseServiceConfig,
            DecisionServiceConfig decisionServiceConfig,
            ProductServiceConfig productServiceConfig,
            RetryHandlerConfig retryHandlerConfig,
            RepositoriesConfig repositoriesConfig
    ) {

        DefaultController defaultController = configDefaultController(inputHandlerConfig, outputHandlerConfig,
                dateProviderConfig, purchaseServiceConfig, decisionServiceConfig, productServiceConfig);
        ControllerRePurchaseProxy controllerRePurchaseProxy = configRePurchase(
                inputHandlerConfig,
                retryHandlerConfig,
                defaultController
        );
        this.controller = configureTransaction(repositoriesConfig, controllerRePurchaseProxy);
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

    private static ControllerRePurchaseProxy configRePurchase(
            InputHandlerConfig inputHandlerConfig,
            RetryHandlerConfig retryHandlerConfig,
            Controller controller
    ) {
        return new ControllerRePurchaseProxy(
                controller,
                inputHandlerConfig.getInputHandler(),
                retryHandlerConfig.getRetryHandler()
        );
    }

    private static ControllerTransactionProxy configureTransaction(
            RepositoriesConfig repositoriesConfig,
            Controller controller
    ) {
        return new ControllerTransactionProxy(
                controller,
                repositoriesConfig.getRepositories());
    }

    public Controller getController() {
        return this.controller;
    }
}
