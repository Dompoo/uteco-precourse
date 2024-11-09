package store.controller;

import java.time.LocalDate;
import java.util.List;
import store.domain.DecisionType;
import store.domain.PurchaseType;
import store.domain.Receipt;
import store.domain.Receipt.ReceiptBuilder;
import store.domain.membership.Membership;
import store.dto.request.PurchaseRequest;
import store.dto.response.ProductResponse;
import store.dto.response.PromotionedProductResponse;
import store.dto.response.PurchaseCostResponse;
import store.dto.response.PurchaseResult;
import store.dto.response.PurchasedProductResponse;
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
        ReceiptBuilder receiptBuilder = new ReceiptBuilder();
        for (PurchaseRequest purchaseRequest : purchaseRequests) {
            processPurchaseRequest(purchaseRequest, receiptBuilder, dateProvider.getDate());
        }
        processMembership(receiptBuilder);
        processReceipt(receiptBuilder);
    }

    private void processGreetingAndProducts(LocalDate localDate) {
        outputHandler.handleGreetings();
        List<ProductResponse> products = productService.readAllProducts(localDate);
        outputHandler.handleProducts(products);
    }

    private void processPurchaseRequest(PurchaseRequest purchaseRequest, ReceiptBuilder receiptBuilder, LocalDate localDate) {
        DecisionType decisionType = decisionService.getDecisionType(purchaseRequest, dateProvider.getDate());
        PurchaseType purchaseType = decisionService.decidePurchaseType(purchaseRequest, decisionType,
                localDate,
                inputHandler::handleFreeProductDecision,
                inputHandler::handleBringDefaultProductBackDecision
        );
        PurchaseResult purchaseResult = purchaseService.purchaseProduct(purchaseRequest, purchaseType, localDate);
        receiptBuilder.addPurchase(purchaseResult);
    }

    private void processMembership(ReceiptBuilder receiptBuilder) {
        Membership membership = decisionService.decideMembership(inputHandler::handleMembershipDecision);
        receiptBuilder.addMembership(membership);
    }

    private void processReceipt(ReceiptBuilder receiptBuilder) {
        Receipt receipt = receiptBuilder.build();
        outputHandler.handlePurchasedProcuts(PurchasedProductResponse.from(receipt));
        outputHandler.handlePromotionedProducts(PromotionedProductResponse.from(receipt));
        outputHandler.handlePurchaseCost(PurchaseCostResponse.from(receipt));
    }
}
