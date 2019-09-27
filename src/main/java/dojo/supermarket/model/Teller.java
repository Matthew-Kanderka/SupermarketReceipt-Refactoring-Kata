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

    public void addSpecialOffer(SpecialOfferType offerType, Product product, double argument) {
        this.offers.put(product, new Offer(offerType, product, argument));
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
                int quantityAsInt = (int) quantity;
                Discount discount = null;

                if (offer.offerType == SpecialOfferType.ThreeForTwo) {
                    if (quantityAsInt > 2) {
                        double discountAmount = get3For2DiscountAmount(quantity, unitPrice, getNumberOfXs(quantityAsInt, offer.offerType.getGroup()));
                        discount = new Discount(p, "3 for 2", discountAmount);
                    }

                } else if (offer.offerType == SpecialOfferType.TwoForAmount) {
                    if (quantityAsInt >= 2) {
                        int numberOfXs = getNumberOfXs(quantityAsInt, offer.offerType.getGroup());
                        discount = getXForYDiscountTotal(p, quantity, offer, unitPrice, numberOfXs, 2);
                    }

                } if (offer.offerType == SpecialOfferType.FiveForAmount) {
                    if (quantityAsInt >= 5) {
                        int numberOfXs = getNumberOfXs(quantityAsInt, offer.offerType.getGroup());
                        discount = getXForYDiscountTotal(p, quantity, offer, unitPrice, numberOfXs, 5);
                    }
                }

                if (offer.offerType == SpecialOfferType.TenPercentDiscount) {
                    double percentDiscount = getPercentDiscount(quantity, unitPrice, offer);
                    discount = new Discount(p, offer.argument + "% off", percentDiscount);
                }

                if (discount != null)
                    receipt.addDiscount(discount);
            }

        }
    }

    Discount getXForYDiscountTotal(Product p, double quantity, Offer offer, double unitPrice, int numberOfXs, int dealGroup) {
        double discountTotal = unitPrice * quantity - (offer.argument * numberOfXs + quantity % dealGroup * unitPrice);
        return new Discount(p, dealGroup + " for " + offer.argument, discountTotal);
    }

    double get3For2DiscountAmount(double quantity, double unitPrice, int numberOfXs) {
        return quantity * unitPrice - ((numberOfXs * 2 * unitPrice) + quantity % 3 * unitPrice);
    }

    double getPercentDiscount(double quantity, double unitPrice, Offer offer) {
        return quantity * unitPrice * offer.argument / 100.0;
    }

    int getNumberOfXs(int productQuantity, int dealGroup) {
        return productQuantity / dealGroup;
    }

}
