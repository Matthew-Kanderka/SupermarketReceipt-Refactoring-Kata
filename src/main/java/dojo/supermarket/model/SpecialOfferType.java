package dojo.supermarket.model;

public enum SpecialOfferType {
    ThreeForTwo(3), TenPercentDiscount(0), TwoForAmount(2), FiveForAmount(5);

    private final int group;

    SpecialOfferType(int group) {
        this.group = group;
    }

    public int getGroup() {
        return group;
    }
}
