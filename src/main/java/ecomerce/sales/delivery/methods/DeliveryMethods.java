package ecomerce.sales.delivery.methods;

public record DeliveryMethods(
        boolean inpost,
        boolean courier,
        boolean special) {
}
