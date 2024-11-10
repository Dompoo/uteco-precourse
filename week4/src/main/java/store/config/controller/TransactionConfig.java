package store.config.controller;

import store.config.infra.repository.RepositoriesConfig;
import store.controller.Controller;
import store.controller.ControllerTransactionProxy;

public class TransactionConfig implements ControllerConfig {

    private final Controller controller;

    public TransactionConfig(
            ControllerConfig controllerConfig,
            RepositoriesConfig repositoriesConfig
    ) {
        this.controller = new ControllerTransactionProxy(
                controllerConfig.getController(),
                repositoriesConfig.getRepositories());
    }

    @Override
    public Controller getController() {
        return this.controller;
    }
}
