package store.io.input;

import java.util.Arrays;
import java.util.List;
import store.dto.request.PurchaseRequest;
import store.io.reader.Reader;
import store.io.writer.Writer;

public class InputHandler {

    private static final String PURCHASE_MESSAGE = "구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])";
    private static final String PURCHASE_SEPARATOR = ",";
    private static final String FREE_PRODUCT_MESSAGE_FORMAT = "현재 %s은(는) %d개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)";
    private static final String BRING_PRODUCT_BACK_MESSAGE = "현재 %s %d개는 프로모션 할인이 적용되지 않습니다. "
            + "그래도 구매하시겠습니까? (Y/N)";
    private static final String MEMBERSHIP_MESSAGE = "멤버십 할인을 받으시겠습니까? (Y/N)";
    private static final String RE_PURCHASE_MESSAGE = "감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)";

    private final Reader reader;
    private final Writer writer;
    private final InputParser inputParser;
    private final InputValidator inputValidator;

    public InputHandler(Reader reader, Writer writer, InputParser inputParser, InputValidator inputValidator) {
        this.reader = reader;
        this.writer = writer;
        this.inputParser = inputParser;
        this.inputValidator = inputValidator;
    }

    public List<PurchaseRequest> handlePurchases() {
        writer.writeLine(PURCHASE_MESSAGE);
        List<String> purchases = Arrays.stream(reader.readLineAsStrings(PURCHASE_SEPARATOR)).toList();
        inputValidator.validatePurchases(purchases);
        List<PurchaseRequest> result = inputParser.parsePurchases(purchases);
        writer.writeEmptyLine();
        return result;
    }

    public boolean handleFreeProductDecision(String productName, int freeCount) {
        writer.writeLine(FREE_PRODUCT_MESSAGE_FORMAT.formatted(productName, freeCount));
        String freeProductDecision = reader.readLineAsString();
        inputValidator.validateYOrN(freeProductDecision);
        boolean result = inputParser.parseYesOrNo(freeProductDecision);
        writer.writeEmptyLine();
        return result;
    }

    public boolean handleBringDefaultProductBackDecision(String productName, int noPromotionCount) {
        writer.writeLine(BRING_PRODUCT_BACK_MESSAGE.formatted(productName, noPromotionCount));
        String bringDefaultProductBackDecision = reader.readLineAsString();
        inputValidator.validateYOrN(bringDefaultProductBackDecision);
        boolean result = inputParser.parseYesOrNo(bringDefaultProductBackDecision);
        writer.writeEmptyLine();
        return result;
    }

    public boolean handleMembershipDecision() {
        writer.writeLine(MEMBERSHIP_MESSAGE);
        String membershipDecision = reader.readLineAsString();
        inputValidator.validateYOrN(membershipDecision);
        boolean result = inputParser.parseYesOrNo(membershipDecision);
        writer.writeEmptyLine();
        return result;
    }

    public boolean handleRePuchase() {
        writer.writeLine(RE_PURCHASE_MESSAGE);
        String rePurchaseDecision = reader.readLineAsString();
        inputValidator.validateYOrN(rePurchaseDecision);
        boolean result = inputParser.parseYesOrNo(rePurchaseDecision);
        writer.writeEmptyLine();
        return result;
    }
}
