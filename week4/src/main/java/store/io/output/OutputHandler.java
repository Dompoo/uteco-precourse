package store.io.output;

import java.util.List;
import store.dto.response.ProductResponse;
import store.dto.response.PromotionedProductResponse;
import store.dto.response.PurchaseCostResponse;
import store.dto.response.PurchasedProductResponse;
import store.io.writer.Writer;

public class OutputHandler {

    private final Writer writer;
    private final OutputParser outputParser;

    public OutputHandler(Writer writer, OutputParser outputParser) {
        this.writer = writer;
        this.outputParser = outputParser;
    }

    public void handleGreetings() {
        writer.writeLine("안녕하세요. W편의점입니다.");
    }

    public void handleProducts(List<ProductResponse> productResponses) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("현재 보유하고 있는 상품입니다.\n\n");
        stringBuilder.append(outputParser.parseProductResponses(productResponses));
        writer.writeLine(stringBuilder.toString());
    }

    public void handlePurchasedProcuts(List<PurchasedProductResponse> purchasedProductResponses) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\n==============W 편의점================\n");
        stringBuilder.append(outputParser.parsePurchasedProductsResponses(purchasedProductResponses));
        writer.writeLine(stringBuilder.toString());
    }

    public void handlePromotionedProducts(List<PromotionedProductResponse> promotionedProductResponses) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("=============증\t\t정===============\n");
        stringBuilder.append(outputParser.parsePromotionedProductsResponses(promotionedProductResponses));
        writer.writeLine(stringBuilder.toString());
    }

    public void handlePurchaseCost(PurchaseCostResponse purchaseCostResponse) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("====================================\n");
        stringBuilder.append(outputParser.parsePurchaseCostResponse(purchaseCostResponse));
        writer.writeLine(stringBuilder.toString());
    }
}
