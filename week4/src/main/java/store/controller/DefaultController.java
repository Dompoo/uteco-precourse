package store.controller;

import java.time.LocalDate;
import java.util.List;
import store.domain.DecisionType;
import store.domain.PurchaseType;
import store.domain.membership.Membership;
import store.domain.receipt.Receipt;
import store.dto.request.PurchaseRequest;
import store.dto.response.ProductResponse;
import store.dto.response.PurchaseResult;
import store.io.input.InputHandler;
import store.io.output.OutputHandler;
import store.service.dateProvider.DateProvider;
import store.service.decisionService.DecisionService;
import store.service.productService.ProductService;
import store.service.purchaseService.PurchaseService;

public class DefaultController implements Controller {

    private final InputHandler inputHandler;
    private final OutputHandler outputHandler;
    private final DateProvider dateProvider;
    private final PurchaseService purchaseService;
    private final DecisionService decisionService;
    private final ProductService productService;

    public DefaultController(InputHandler inputHandler, OutputHandler outputHandler, DateProvider dateProvider,
                             PurchaseService purchaseService, DecisionService decisionService,
                             ProductService productService) {
        this.inputHandler = inputHandler;
        this.outputHandler = outputHandler;
        this.dateProvider = dateProvider;
        this.purchaseService = purchaseService;
        this.decisionService = decisionService;
        this.productService = productService;
    }

    public void run() {
        processGreetingAndProducts(dateProvider.getDate());
        List<PurchaseRequest> purchaseRequests = purchaseService.getPurchases(inputHandler::handlePurchases);
        Receipt receipt = new Receipt();
        for (PurchaseRequest purchaseRequest : purchaseRequests) {
            processPurchaseRequest(purchaseRequest, receipt, dateProvider.getDate());
        }
        processMembership(receipt);
        processReceipt(receipt);
    }

    private void processGreetingAndProducts(LocalDate localDate) {
        outputHandler.handleGreetings();
        List<ProductResponse> products = productService.readAllProducts(localDate);
        outputHandler.handleProducts(products);
    }

    private void processPurchaseRequest(PurchaseRequest purchaseRequest, Receipt receipt, LocalDate localDate) {
        DecisionType decisionType = decisionService.getDecisionType(purchaseRequest, dateProvider.getDate());
        PurchaseType purchaseType = decisionService.decidePurchaseType(purchaseRequest, decisionType,
                localDate,
                inputHandler::handleFreeProductDecision,
                inputHandler::handleBringDefaultProductBackDecision
        );
        PurchaseResult purchaseResult = purchaseService.purchaseProduct(purchaseRequest, purchaseType, localDate);
        receipt.addPurchase(purchaseResult);
    }

    private void processMembership(Receipt receipt) {
        Membership membership = decisionService.decideMembership(inputHandler::handleMembershipDecision);
        receipt.addMembership(membership);
    }

    private void processReceipt(Receipt receipt) {
        outputHandler.handlePurchasedProcuts(receipt.buildPurchasedProductResponses());
        outputHandler.handlePromotionedProducts(receipt.buildPromotionedProductResponses());
        outputHandler.handlePurchaseCost(receipt.buildPurchaseCostResponse());
    }
}
