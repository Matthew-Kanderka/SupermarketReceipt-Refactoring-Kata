package dojo.supermarket.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


public class ProductTest {

    @Test
    public void testEquals() {
        Product greenApple = new Product("apple", ProductUnit.Kilo);
        Product macintoshApple = new Product("apple", ProductUnit.Kilo);
        Product banana = new Product("banana", ProductUnit.Kilo);

        assertEquals(greenApple, macintoshApple);
        assertNotEquals(greenApple, banana);
    }
}
