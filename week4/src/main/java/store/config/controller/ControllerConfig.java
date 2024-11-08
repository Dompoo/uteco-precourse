package store.config.controller;

import store.config.aop.RetryHandlerConfig;
import store.config.io.inputHandler.InputHandlerConfig;
import store.config.io.outputHandler.OutputHandlerConfig;
import store.config.service.dateProvider.DateProviderConfig;
import store.config.service.decisionService.DecisionServiceConfig;
import store.config.service.productService.ProductServiceConfig;
import store.config.service.purchaseService.PurchaseServiceConfig;
import store.controller.Controller;
import store.controller.DefaultController;
import store.controller.RePurchaseControllerProxy;

public class ControllerConfig {

    private final Controller controller;

    public ControllerConfig(
            InputHandlerConfig inputHandlerConfig,
            OutputHandlerConfig outputHandlerConfig,
            DateProviderConfig dateProviderConfig,
            PurchaseServiceConfig purchaseServiceConfig,
            DecisionServiceConfig decisionServiceConfig,
            ProductServiceConfig productServiceConfig,
            RetryHandlerConfig retryHandlerConfig
    ) {
        DefaultController defaultController = configDefaultController(inputHandlerConfig, outputHandlerConfig,
                dateProviderConfig, purchaseServiceConfig, decisionServiceConfig, productServiceConfig);
        this.controller = configRePurchaseController(
                inputHandlerConfig,
                retryHandlerConfig,
                defaultController
        );
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
            RetryHandlerConfig retryHandlerConfig,
            Controller controller
    ) {
        return new RePurchaseControllerProxy(
                controller,
                inputHandlerConfig.getInputHandler(),
                retryHandlerConfig.getRetryHandler()
        );
    }

    public Controller getController() {
        return this.controller;
    }
}
