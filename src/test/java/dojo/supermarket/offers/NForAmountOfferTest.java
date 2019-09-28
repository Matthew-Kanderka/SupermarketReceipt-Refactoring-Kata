package dojo.supermarket.offers;

import dojo.supermarket.model.Discount;
import dojo.supermarket.model.Product;
import dojo.supermarket.model.ProductUnit;
import dojo.supermarket.model.SpecialOfferType;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class NForAmountOfferTest {

    @Test
    public void test2ForAmount() {
        Product apple = new Product("apple", ProductUnit.Kilo);

        NForAmountOffer testClass = new NForAmountOffer(SpecialOfferType.TwoForAmount, apple, 1.0);
        assertEquals(1.00, testClass.getXForYDiscountTotal(2.0, 1.00, 1, 2));
    }

    @Test
    public void testGet2ForAmountDiscount() {
        Product apple = new Product("apple", ProductUnit.Kilo);
        NForAmountOffer testClass = new NForAmountOffer(SpecialOfferType.TwoForAmount, apple, 1.0);

        Discount result = testClass.calculateDiscount(2, 1.50);

        assertEquals(2.0, result.getDiscountAmount());
        assertEquals("2 for 1.0", result.getDescription());
        assertEquals(apple, result.getProduct());

        result = testClass.calculateDiscount(4, 1.50);

        assertEquals(4.0, result.getDiscountAmount());
        assertEquals("2 for 1.0", result.getDescription());
        assertEquals(apple, result.getProduct());

        assertNull(testClass.calculateDiscount(0, 1.50));
        assertNull(testClass.calculateDiscount(1, 1.50));
    }

    @Test
    public void test5ForAmount() {
        Product apple = new Product("apple", ProductUnit.Kilo);

        NForAmountOffer testClass = new NForAmountOffer(SpecialOfferType.FiveForAmount, apple, 2.0);
        assertEquals(3.00, testClass.getXForYDiscountTotal(5.0, 1.00, 1, 5));
    }

    @Test
    public void testGet5ForAmountDiscount() {
        Product apple = new Product("apple", ProductUnit.Kilo);
        NForAmountOffer testClass = new NForAmountOffer(SpecialOfferType.FiveForAmount, apple, 2.0);

        Discount result = testClass.calculateDiscount(5, 1.00);

        assertEquals(3.0,result.getDiscountAmount());
        assertEquals("5 for 2.0", result.getDescription());
        assertEquals(apple, result.getProduct());

        result = testClass.calculateDiscount(10, 1.00);

        assertEquals(6.0,result.getDiscountAmount());
        assertEquals("5 for 2.0", result.getDescription());
        assertEquals(apple, result.getProduct());

        assertNull(testClass.calculateDiscount(0, 1.50));
        assertNull(testClass.calculateDiscount(1, 1.50));
    }
}
