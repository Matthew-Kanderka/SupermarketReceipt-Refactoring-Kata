package dojo.supermarket.offers;

import dojo.supermarket.model.Discount;
import dojo.supermarket.model.Offer;
import dojo.supermarket.model.Product;
import dojo.supermarket.model.SpecialOfferType;

public class NForAmountOffer extends Offer {

    public NForAmountOffer(SpecialOfferType offerType, Product product, double argument) {
        super(offerType, product, argument);
    }

    @Override
    public Discount calculateDiscount(double quantity, double unitPrice) {
        Discount discount;
        int quantityAsInt = (int) quantity;
        if (quantityAsInt >= offerType.getGroup()) {
            int numberOfXs = getNumberOfXs(quantityAsInt, offerType.getGroup());
            double discountTotal = getXForYDiscountTotal(quantity, unitPrice, numberOfXs, offerType.getGroup());
            discount = new Discount(product, offerType.getGroup() + " for " + argument, discountTotal);
        } else {
            discount = null;
        }
        return discount;
    }

    double getXForYDiscountTotal(double quantity, double unitPrice, int numberOfXs, int dealGroup) {
        return unitPrice * quantity - (argument * numberOfXs + quantity % dealGroup * unitPrice);
    }
}
