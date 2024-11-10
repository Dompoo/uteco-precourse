package store.controller;

import java.util.List;
import store.common.dto.request.PurchaseRequest;
import store.common.dto.response.ProductResponse;
import store.common.dto.response.PurchaseResult;
import store.domain.DecisionType;
import store.domain.PurchaseType;
import store.domain.membership.Membership;
import store.domain.receipt.Receipt;
import store.io.input.InputHandler;
import store.io.output.OutputHandler;
import store.service.decisionService.DecisionService;
import store.service.productService.ProductService;
import store.service.purchaseService.PurchaseService;

public class DefaultController implements Controller {

    private final InputHandler inputHandler;
    private final OutputHandler outputHandler;
    private final PurchaseService purchaseService;
    private final DecisionService decisionService;
    private final ProductService productService;

    public DefaultController(InputHandler inputHandler, OutputHandler outputHandler,
                             PurchaseService purchaseService, DecisionService decisionService,
                             ProductService productService) {
        this.inputHandler = inputHandler;
        this.outputHandler = outputHandler;
        this.purchaseService = purchaseService;
        this.decisionService = decisionService;
        this.productService = productService;
    }

    public void run() {
        processGreetingAndProducts();
        List<PurchaseRequest> purchaseRequests = purchaseService.getPurchases(inputHandler::handlePurchases);
        Receipt receipt = new Receipt();
        for (PurchaseRequest purchaseRequest : purchaseRequests) {
            processPurchaseRequest(purchaseRequest, receipt);
        }
        processMembership(receipt);
        processReceipt(receipt);
    }

    private void processGreetingAndProducts() {
        outputHandler.handleGreetings();
        List<ProductResponse> products = productService.readAllProducts();
        outputHandler.handleProducts(products);
    }

    private void processPurchaseRequest(PurchaseRequest purchaseRequest, Receipt receipt) {
        DecisionType decisionType = decisionService.getDecisionType(purchaseRequest);
        PurchaseType purchaseType = decisionService.decidePurchaseType(purchaseRequest, decisionType,
                inputHandler::handleFreeProductDecision,
                inputHandler::handleBringDefaultProductBackDecision
        );
        PurchaseResult purchaseResult = purchaseService.purchaseProduct(purchaseRequest, purchaseType);
        receipt.addPurchase(purchaseResult);
    }

    private void processMembership(Receipt receipt) {
        Membership membership = decisionService.decideMembership(inputHandler::handleMembershipDecision);
        receipt.applyMembership(membership);
    }

    private void processReceipt(Receipt receipt) {
        outputHandler.handlePurchasedProcuts(receipt.buildPurchasedProductResponses());
        outputHandler.handlePromotionedProducts(receipt.buildPromotionedProductResponses());
        outputHandler.handlePurchaseCost(receipt.buildPurchaseCostResponse());
    }
}
