import dojo.supermarket.ReceiptPrinter;
import dojo.supermarket.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ReceiptPrinterTest {

    private Receipt testReceipt;
    private ReceiptPrinter testPrinter;

    @BeforeEach
    void init() {
        testPrinter = new ReceiptPrinter();

        Product apple = new Product("apple", ProductUnit.Kilo);
        Product banana = new Product("banana", ProductUnit.Kilo);

        ReceiptItem appleItem = new ReceiptItem(apple, 2.0, 1.99, 3.98);
        ReceiptItem bananaItem = new ReceiptItem(banana, 1.50, 1.00, 1.50);

        testReceipt = new Receipt();
        testReceipt.addProduct(appleItem);
        testReceipt.addProduct(bananaItem);
    }

    @Test
    public void testPrintReceipt() {

        String result = testPrinter.printReceipt(testReceipt);

        assert result.contains("apple");
        assert result.contains("banana");
        assert result.contains("5.48");
    }

    @Test
    public void testPrintReceipt_withDiscount() {

        Discount appleDiscount = new Discount(testReceipt.getItems().get(0).getProduct(), "sale", 1.00);

        testReceipt.addDiscount(appleDiscount);

        String result = testPrinter.printReceipt(testReceipt);

        assert result.contains("apple");
        assert result.contains("banana");
        assert result.contains("4.48");
    }

    @Test
    public void testNullReceipt() {
        assertThrows(NullPointerException.class, () -> {
            testPrinter.printReceipt(null);
        });
    }
}
