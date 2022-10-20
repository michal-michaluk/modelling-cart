package ecomerce.sales.cart;

public record Availability(WarehouseStock stock) {
    enum WarehouseStock {
        HIGH_AMOUNT,
        LOW_AMOUNT,
        OUT_OF_STOCK,
        DISCONTINUED
    }

    public boolean available() {
        return stock == WarehouseStock.HIGH_AMOUNT
                || stock == WarehouseStock.LOW_AMOUNT;
    }
}
