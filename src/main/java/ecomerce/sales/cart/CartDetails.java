package ecomerce.sales.cart;

import ecomerce.sales.delivery.methods.DeliveryMethods;
import lombok.Value;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Value
class CartDetails {

    List<Item> items;
    Money total;
    DeliveryMethods deliveryMethods;
    boolean checkoutEnabled;

    static CartDetailsBuilder builder() {
        return new CartDetailsBuilder();
    }

    record Item(ProductDetails product, int amount) {
        public boolean available() {
            return product.availability().available();
        }
    }

    record ProductDetails(String productId,
                          String title,
                          URL photo,
                          Money price,
                          Availability availability) {
    }

    public static class CartDetailsBuilder {
        private final List<Item> items = new ArrayList<>();
        private Money total = Money.zero();
        DeliveryMethods deliveryMethods;

        CartDetailsBuilder() {
        }

        public CartDetailsBuilder add(ProductDetails product, int amount) {
            this.items.add(new Item(product, amount));
            total = total.add(product.price.multiply(amount));
            return this;
        }

        public CartDetailsBuilder deliveryMethods(DeliveryMethods deliveryMethods) {
            this.deliveryMethods = deliveryMethods;
            return this;
        }

        public CartDetails build() {
            return new CartDetails(items, total, deliveryMethods, allProductsAvailable());
        }

        private boolean allProductsAvailable() {
            return items.stream()
                    .filter(Item::available)
                    .findFirst()
                    .isEmpty();
        }
    }
}
