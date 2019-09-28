package dojo.supermarket.offers;

import dojo.supermarket.model.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ThreeForTwoOfferTest {

    @Test
    public void testGetDiscountTotal() {
        Product apples = new Product("apples", ProductUnit.Kilo);
        ThreeForTwoOffer testClass = new ThreeForTwoOffer(SpecialOfferType.ThreeForTwo, apples, 2);

        double result = testClass.get3For2DiscountAmount(3.0, 1.50,  1);
        assertEquals(1.5, result);
    }

    @Test
    public void testGetDiscount() {
        Product apples = new Product("apples", ProductUnit.Kilo);
        ThreeForTwoOffer testClass = new ThreeForTwoOffer(SpecialOfferType.ThreeForTwo, apples, 2.0);

        Discount result = testClass.calculateDiscount(3.0, 1.00);

        assertEquals(1.0,result.getDiscountAmount());
        assertEquals("3 for 2", result.getDescription());
        assertEquals(apples, result.getProduct());

        result = testClass.calculateDiscount(6.0, 1.00);

        assertEquals(2.0,result.getDiscountAmount());
        assertEquals("3 for 2", result.getDescription());
        assertEquals(apples, result.getProduct());

        assertNull(testClass.calculateDiscount(2.0, 1.00));
        assertNull(testClass.calculateDiscount(0.0, 1.00));
    }
}
