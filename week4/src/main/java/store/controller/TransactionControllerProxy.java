package store.controller;

import java.util.List;
import store.infra.repository.Repository;

public class TransactionControllerProxy implements Controller {

    private final Controller targetController;
    private final List<Repository<?>> repositories;

    public TransactionControllerProxy(Controller targetController, List<Repository<?>> repositories) {
        this.targetController = targetController;
        this.repositories = repositories;
    }


    @Override
    public void run() {
        targetController.run();
        for (Repository<?> repository : repositories) {
            repository.updateDatabaseInBatch();
        }
    }
}
