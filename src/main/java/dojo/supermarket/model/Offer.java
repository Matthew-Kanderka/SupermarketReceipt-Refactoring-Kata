package dojo.supermarket.model;

public abstract class Offer {
    public SpecialOfferType offerType;
    public final Product product;
    public double argument;

    public Offer(SpecialOfferType offerType, Product product, double argument) {
        this.offerType = offerType;
        this.argument = argument;
        this.product = product;
    }

    Product getProduct() {
        return this.product;
    }

    public int getNumberOfXs(int productQuantity, int dealGroup) {
        return productQuantity / dealGroup;
    }

    public abstract Discount calculateDiscount(double quantity, double unitPrice);
}
