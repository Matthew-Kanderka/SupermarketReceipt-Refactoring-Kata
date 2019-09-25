package dojo.supermarket.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class ReceiptTest {

    @Test
    void testGetTotalPrice_singleItem() {
        assertEquals(4.50, createTestReceipt().getTotalPrice(), 0.01);
    }

    @Test
    void testGetTotalPrice_2Items() {
        Receipt receipt2 = createTestReceipt();
        Product product2 = new Product("bread", ProductUnit.Each);
        ReceiptItem item2 = new ReceiptItem(product2, 2.0, 3.75, 7.50);
        receipt2.addProduct(item2);

        assertEquals(12.00, receipt2.getTotalPrice(),0.01);
    }

    @Test
    void testGetTotalPrice_zeroItems() {
        Receipt receipt = new Receipt();
        assertEquals(0.00, receipt.getTotalPrice(), 0.01);
    }

    @Test
    void testGetTotalPrice_1Item_withDiscount() {
        Receipt receipt2 = createTestReceipt();
        Product bread = new Product("bread", ProductUnit.Each);
        ReceiptItem item2 = new ReceiptItem(bread, 2.0, 3.75, 7.50);
        receipt2.addProduct(item2);
        receipt2.addDiscount(new Discount(bread, "2 for 5.0", 2.50));

        assertEquals(9.50, receipt2.getTotalPrice(),0.01);
    }

    Receipt createTestReceipt() {
        Receipt receipt = new Receipt();

        Product product1 = new Product("cheese", ProductUnit.Each);
        ReceiptItem item1 = new ReceiptItem(product1, 1.0, 4.50, 4.50);

        receipt.addProduct(item1);
        return receipt;
    }
}
