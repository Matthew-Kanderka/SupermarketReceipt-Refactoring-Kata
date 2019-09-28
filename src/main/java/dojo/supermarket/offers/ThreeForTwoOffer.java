package dojo.supermarket.offers;

import dojo.supermarket.model.Discount;
import dojo.supermarket.model.Offer;
import dojo.supermarket.model.Product;
import dojo.supermarket.model.SpecialOfferType;

public class ThreeForTwoOffer extends Offer {

    public ThreeForTwoOffer(SpecialOfferType offerType, Product product, double argument) {
        super(offerType, product, argument);
    }

    @Override
    public Discount calculateDiscount(double quantity, double unitPrice) {
        Discount discount;
        if ((int) quantity > 2) {
            double discountAmount = get3For2DiscountAmount(quantity, unitPrice, getNumberOfXs((int) quantity, offerType.getGroup()));
            discount = new Discount(product, "3 for 2", discountAmount);
        } else {
            discount = null;
        }
        return discount;
    }

    double get3For2DiscountAmount(double quantity, double unitPrice, int numberOfXs) {
        return quantity * unitPrice - ((numberOfXs * 2 * unitPrice) + quantity % 3 * unitPrice);
    }
}
