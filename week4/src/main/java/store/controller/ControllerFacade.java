package store.controller;

import java.util.List;
import store.domain.DecisionType;
import store.domain.PurchaseType;
import store.domain.Receipt;
import store.domain.Receipt.ReceiptBuilder;
import store.domain.membership.RatioMembership;
import store.dto.request.PurchaseRequest;
import store.dto.response.ProductResponse;
import store.dto.response.PromotionedProductResponse;
import store.dto.response.PurchaseCostResponse;
import store.dto.response.PurchaseResult;
import store.dto.response.PurchasedProductResponse;
import store.io.input.InputHandler;
import store.io.output.OutputHandler;
import store.service.Service;
import store.service.dateProvider.DateProvider;

public class ControllerFacade implements Controller {

    private final InputHandler inputHandler;
    private final OutputHandler outputHandler;
    private final DateProvider dateProvider;
    private final Service service;

    public ControllerFacade(InputHandler inputHandler, OutputHandler outputHandler, DateProvider dateProvider,
                            Service service) {
        this.inputHandler = inputHandler;
        this.outputHandler = outputHandler;
        this.dateProvider = dateProvider;
        this.service = service;
    }

    public void run() {
        outputHandler.handleGreetings();
        List<ProductResponse> products = service.readAllProducts();
        outputHandler.handleProducts(products);
        List<PurchaseRequest> purchaseRequests = service.getPurchases(inputHandler::handlePurchases);

        ReceiptBuilder receiptBuilder = new ReceiptBuilder();
        for (PurchaseRequest purchaseRequest : purchaseRequests) {
            DecisionType decisionType = service.getDecisionType(purchaseRequest, dateProvider.getDate());
            PurchaseType purchaseType = service.decidePurchaseType(purchaseRequest, decisionType,
                    inputHandler::handleFreeProductDecision,
                    inputHandler::handleBringDefaultProductBackDecision
            );
            PurchaseResult purchaseResult = service.purchaseProduct(purchaseRequest, purchaseType);
            receiptBuilder.addPurchase(purchaseResult);
        }

        boolean membership = inputHandler.handleMembershipDecision();
        if (membership) {
            receiptBuilder.addMembership(new RatioMembership());
        }

        Receipt receipt = receiptBuilder.build();

        outputHandler.handlePurchasedProcuts(PurchasedProductResponse.from(receipt));
        outputHandler.handlePromotionedProducts(PromotionedProductResponse.from(receipt));
        outputHandler.handlePurchaseCost(PurchaseCostResponse.from(receipt));
    }
}
