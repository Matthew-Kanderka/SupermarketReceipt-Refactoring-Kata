package dojo.supermarket.offers;

import dojo.supermarket.model.Discount;
import dojo.supermarket.model.Offer;
import dojo.supermarket.model.Product;
import dojo.supermarket.model.SpecialOfferType;

public class TenPercentDiscountOffer extends Offer {

    public TenPercentDiscountOffer(SpecialOfferType offerType, Product product, double argument) {
        super(offerType, product, argument);
    }

    @Override
    public Discount calculateDiscount(double quantity, double unitPrice) {
        Discount discount;
        double percentDiscount = getPercentDiscount(quantity, unitPrice);
        discount = new Discount(product, argument + "% off", percentDiscount);
        return discount;
    }

    double getPercentDiscount(double quantity, double unitPrice) {
        return quantity * unitPrice * argument / 100.0;
    }
}
