package store.controller;

import store.aop.RetryHandler;
import store.io.input.InputHandler;

public class RePurchaseControllerProxy implements Controller {

    private final Controller targetController;
    private final InputHandler inputHandler;
    private final RetryHandler retryHandler;

    public RePurchaseControllerProxy(Controller targetController, InputHandler inputHandler,
                                     RetryHandler retryHandler) {
        this.targetController = targetController;
        this.inputHandler = inputHandler;
        this.retryHandler = retryHandler;
    }

    @Override
    public void run() {
        do {
            targetController.run();
        } while (retryHandler.tryUntilSuccess(inputHandler::handleRePuchase));
    }
}
