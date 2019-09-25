package dojo.supermarket.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class TellerTest {

    @Test
    public void testGetTotalPrice() {
        SupermarketCatalog testCatalog = new FakeCatalog();
        Teller testClass = new Teller(testCatalog);

        assertEquals(0.0, testClass.getTotalPrice(1.0, 0.0));
        assertEquals(5.0, testClass.getTotalPrice(1.0, 5.0));
        assertEquals(10.0, testClass.getTotalPrice(2.0, 5.0));
    }

    @Test
    public void testCheckOutArticles_oneItem() {
        SupermarketCatalog testCatalog = new FakeCatalog();
        Product apples = new Product("apples", ProductUnit.Kilo);

        testCatalog.addProduct(apples, 1.50);

        Teller testClass = new Teller(testCatalog);
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.addItemQuantity(apples, 10.0);

        Receipt result = testClass.checksOutArticlesFrom(shoppingCart);
        assertEquals(1,result.getItems().size());
        assertEquals(15.0,result.getTotalPrice(), 0.01);
        assertEquals(Collections.emptyList(),result.getDiscounts());
    }

    @Test
    public void testCheckOutArticles_oneItem_withDiscount() {
        SupermarketCatalog testCatalog = new FakeCatalog();
        Product apples = new Product("apples", ProductUnit.Kilo);

        testCatalog.addProduct(apples, 1.50);

        Teller testClass = new Teller(testCatalog);
        testClass.addSpecialOffer(SpecialOfferType.FiveForAmount, apples, 2.0);

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.addItemQuantity(apples, 10.0);

        Receipt result = testClass.checksOutArticlesFrom(shoppingCart);
        assertEquals(1,result.getItems().size());
        assertEquals(4.0,result.getTotalPrice(), 0.01);

        List<Discount> discountList = new ArrayList<>();
        discountList.add(new Discount(apples, "5.0 for 2.0", 11));
        assertEquals(1, result.getDiscounts().size());
        assertEquals(11.0, result.getDiscounts().get(0).getDiscountAmount());
        assertEquals(apples, result.getDiscounts().get(0).getProduct());
        assertEquals("5 for 2.0", result.getDiscounts().get(0).getDescription());
    }

    @Test
    public void testCheckOutArticles_emptyCart() {
        SupermarketCatalog testCatalog = new FakeCatalog();
        Product apples = new Product("apples", ProductUnit.Kilo);

        testCatalog.addProduct(apples, 1.50);

        Teller testClass = new Teller(testCatalog);
        ShoppingCart shoppingCart = new ShoppingCart();

        Receipt result = testClass.checksOutArticlesFrom(shoppingCart);
        assertEquals(0,result.getItems().size());
        assertEquals(0.0,result.getTotalPrice(), 0.01);
        assertEquals(Collections.emptyList(),result.getDiscounts());
    }
}
