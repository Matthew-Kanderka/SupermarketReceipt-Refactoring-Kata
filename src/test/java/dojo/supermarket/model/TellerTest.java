package dojo.supermarket.model;

import org.junit.jupiter.api.Test;

import java.util.Collections;

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
    public void testCheckOutArticles_oneItem_withFiveForAmountDiscount() {
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

        assertEquals(1, result.getDiscounts().size());
        assertEquals(11.0, result.getDiscounts().get(0).getDiscountAmount());
        assertEquals(apples, result.getDiscounts().get(0).getProduct());
        assertEquals("5 for 2.0", result.getDiscounts().get(0).getDescription());
    }

    @Test
    public void testCheckOutArticles_oneItem_withTwoForAmountDiscount() {
        SupermarketCatalog testCatalog = new FakeCatalog();
        Product apples = new Product("apples", ProductUnit.Kilo);

        testCatalog.addProduct(apples, 1.50);

        Teller testClass = new Teller(testCatalog);
        testClass.addSpecialOffer(SpecialOfferType.TwoForAmount, apples, 1.0);

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.addItemQuantity(apples, 10.0);

        Receipt result = testClass.checksOutArticlesFrom(shoppingCart);
        assertEquals(1,result.getItems().size());
        assertEquals(5.0,result.getTotalPrice(), 0.01);

        assertEquals(1, result.getDiscounts().size());
        assertEquals(10.0, result.getDiscounts().get(0).getDiscountAmount());
        assertEquals(apples, result.getDiscounts().get(0).getProduct());
        assertEquals("2 for 1.0", result.getDiscounts().get(0).getDescription());
    }

    @Test
    public void testCheckOutArticles_oneItem_withThreeForTwoDiscount() {
        SupermarketCatalog testCatalog = new FakeCatalog();
        Product apples = new Product("apples", ProductUnit.Kilo);

        testCatalog.addProduct(apples, 1.50);

        Teller testClass = new Teller(testCatalog);
        testClass.addSpecialOffer(SpecialOfferType.ThreeForTwo, apples, 2.0);

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.addItemQuantity(apples, 3.0);

        Receipt result = testClass.checksOutArticlesFrom(shoppingCart);
        assertEquals(1,result.getItems().size());
        assertEquals(3.0,result.getTotalPrice(), 0.01);

        assertEquals(1, result.getDiscounts().size());
        assertEquals(1.50, result.getDiscounts().get(0).getDiscountAmount());
        assertEquals(apples, result.getDiscounts().get(0).getProduct());
        assertEquals("3 for 2", result.getDiscounts().get(0).getDescription());
    }

    @Test
    public void tenPercentDiscount() {
        SupermarketCatalog catalog = new FakeCatalog();
        Product toothbrush = new Product("toothbrush", ProductUnit.Each);
        catalog.addProduct(toothbrush, 0.99);
        Product apples = new Product("apples", ProductUnit.Kilo);
        catalog.addProduct(apples, 1.99);

        ShoppingCart cart = new ShoppingCart();
        cart.addItemQuantity(apples, 2.5);

        Teller teller = new Teller(catalog);
        teller.addSpecialOffer(SpecialOfferType.TenPercentDiscount, apples, 10.0);

        // ACT
        Receipt receipt = teller.checksOutArticlesFrom(cart);

        // ASSERT
        assertEquals(4.4775, receipt.getTotalPrice(), 0.01);
        assertEquals(0.4975, receipt.getDiscounts().get(0).getDiscountAmount());
        assertEquals(apples, receipt.getDiscounts().get(0).getProduct());
        assertEquals(1, receipt.getItems().size());
        ReceiptItem receiptItem = receipt.getItems().get(0);
        assertEquals(apples, receiptItem.getProduct());
        assertEquals(1.99, receiptItem.getPrice());
        assertEquals(2.5*1.99, receiptItem.getTotalPrice());
        assertEquals(2.5, receiptItem.getQuantity());

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

    @Test
    public void testGetDiscountTotal() {
        SupermarketCatalog testCatalog = new FakeCatalog();
        Product apples = new Product("apples", ProductUnit.Kilo);
        Offer offer = new Offer(SpecialOfferType.ThreeForTwo, apples, 2);

        testCatalog.addProduct(apples, 1.50);

        Teller testClass = new Teller(testCatalog);
        testClass.addSpecialOffer(SpecialOfferType.ThreeForTwo, apples, 2.0);

        double result = testClass.get3For2DiscountAmount(3.0, 1.50,  1);

        assertEquals(1.5, result);
    }

    @Test
    public void testGetPercentDiscount() {
        SupermarketCatalog testCatalog = new FakeCatalog();
        Product apples = new Product("apples", ProductUnit.Kilo);
        Offer offer = new Offer(SpecialOfferType.TenPercentDiscount, apples, 10.0);

        testCatalog.addProduct(apples, 1.50);

        Teller testClass = new Teller(testCatalog);

        assertEquals(0.15, testClass.getPercentDiscount(1, 1.50, offer));
        assertEquals(0.30, testClass.getPercentDiscount(2, 1.50, offer));
        assertEquals(0.0, testClass.getPercentDiscount(0, 1.50, offer));
    }
}
