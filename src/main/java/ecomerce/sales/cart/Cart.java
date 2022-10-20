package ecomerce.sales.cart;

import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.BiConsumer;

@AllArgsConstructor
class Cart {

    final UUID id;
    private final List<Item> items;

    void put(Product product, int amount) {
        requiresAvailableProducts(product);
        itemFor(product).ifPresentOrElse(
                item -> item.amount = amount,
                () -> items.add(new Item(amount, product))
        );
    }

    void remove(String productId) {
        items.removeIf(item -> item.matches(productId));
    }

    private static void requiresAvailableProducts(Product product) {
        if (!product.available()) {
            throw new IllegalArgumentException("product is not available");
        }
    }

    private Optional<Item> itemFor(Product product) {
        return items.stream()
                .filter(item -> item.matches(product))
                .findFirst();
    }

    void forEach(BiConsumer<Integer, Product> function) {
        items.forEach(item -> function.accept(item.amount, item.product));
    }

    public List<String> productIds() {
        return items.stream()
                .map(i -> i.product.id)
                .toList();
    }

    static class Item {
        int amount;
        Product product;

        public Item(int amount, Product product) {
            this.amount = amount;
            this.product = product;
        }

        boolean matches(Product other) {
            return product.id().equals(other.id());
        }

        boolean matches(String productId) {
            return product.id().equals(productId);
        }
    }

    record Product(String id, boolean available) {
    }
}
