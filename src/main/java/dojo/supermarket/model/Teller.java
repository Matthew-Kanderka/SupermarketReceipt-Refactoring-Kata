package dojo.supermarket.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Teller {

    private final SupermarketCatalog catalog;
    private Map<Product, Offer> offers = new HashMap<>();

    public Teller(SupermarketCatalog catalog) {
        this.catalog = catalog;
    }

    public void addSpecialOffer(Product product, Offer offer) {
        this.offers.put(product, offer);
    }

    public Receipt checksOutArticlesFrom(ShoppingCart theCart) {
        Receipt receipt = new Receipt();
        List<ProductQuantity> productQuantities = theCart.getItems();
        for (ProductQuantity pq: productQuantities) {
            Product p = pq.getProduct();
            double quantity = pq.getQuantity();
            double unitPrice = this.catalog.getUnitPrice(p);
            ReceiptItem newReceiptItem = new ReceiptItem(p, quantity, unitPrice, getTotalPrice(quantity, unitPrice));
            receipt.addProduct(newReceiptItem);
        }
        handleOffers(theCart, receipt, this.offers, this.catalog);

        return receipt;
    }

    public double getTotalPrice(Double quantity, Double unitPrice) {
        return quantity * unitPrice;
    }

    void handleOffers(ShoppingCart shoppingCart, Receipt receipt, Map<Product, Offer> offers, SupermarketCatalog catalog) {
        for (Product p: shoppingCart.productQuantities().keySet()) {
            double quantity = shoppingCart.productQuantities.get(p);
            if (offers.containsKey(p)) {
                Offer offer = offers.get(p);
                double unitPrice = catalog.getUnitPrice(p);
                Discount discount = null;

                discount = offer.calculateDiscount(quantity, unitPrice);

                if (discount != null)
                    receipt.addDiscount(discount);
            }

        }
    }
}
