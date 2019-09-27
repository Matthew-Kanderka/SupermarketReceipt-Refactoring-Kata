package dojo.supermarket.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class ReceiptItemTest {

    @Test
    public void testEquals() {
        Product greenApple = new Product("apple", ProductUnit.Kilo);
        Product macintoshApple = new Product("apple", ProductUnit.Kilo);
        Product banana = new Product("banana", ProductUnit.Kilo);

        ReceiptItem appleItem = new ReceiptItem(greenApple, 5.0, 1.50, 7.50);
        ReceiptItem appleItem2 = new ReceiptItem(macintoshApple, 5.0, 1.50, 7.50);
        ReceiptItem bananaItem = new ReceiptItem(banana, 2.0, 1.00, 2.00);

        assertEquals(appleItem, appleItem2);
        assertNotEquals(greenApple, bananaItem);
    }
}
