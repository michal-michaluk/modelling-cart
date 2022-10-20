package ecomerce.sales.delivery.methods;

record Weight(int weight) {
    public static Weight ofKiloGrams(int weight) {
        return new Weight(weight * 1000);
    }

    public int weightInGrams() {
        return weight;
    }

}
