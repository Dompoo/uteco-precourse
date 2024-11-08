package store.controller;

import store.io.input.InputHandler;

public class RePurchaseControllerProxy implements Controller {

    private final Controller targetController;
    private final InputHandler inputHandler;

    public RePurchaseControllerProxy(Controller targetController, InputHandler inputHandler) {
        this.targetController = targetController;
        this.inputHandler = inputHandler;
    }

    @Override
    public void run() {
        do {
            targetController.run();
        } while (inputHandler.handleRePuchase());
    }
}
