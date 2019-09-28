package dojo.supermarket.offers;

import dojo.supermarket.model.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class TenPercentDiscountOfferTest {

    @Test
    public void testGetPercentDiscount() {
        Product apples = new Product("apples", ProductUnit.Kilo);
        TenPercentDiscountOffer testClass = new TenPercentDiscountOffer(SpecialOfferType.TenPercentDiscount, apples, 10.0);

        assertEquals(0.15, testClass.getPercentDiscount(1, 1.50));
        assertEquals(0.30, testClass.getPercentDiscount(2, 1.50));
        assertEquals(0.0, testClass.getPercentDiscount(0, 1.50));
    }

    @Test
    public void testCalculateDiscount() {
        Product apples = new Product("apples", ProductUnit.Kilo);
        TenPercentDiscountOffer testClass = new TenPercentDiscountOffer(SpecialOfferType.TenPercentDiscount, apples, 10.0);

        Discount result = testClass.calculateDiscount(1, 1.50);

        assertEquals(0.15, result.getDiscountAmount());
        assertEquals(apples, result.getProduct());
        assertEquals("10.0% off", result.getDescription());

        result = testClass.calculateDiscount(2, 1.50);

        assertEquals(0.30, result.getDiscountAmount());
        assertEquals(apples, result.getProduct());
        assertEquals("10.0% off", result.getDescription());
    }


}
