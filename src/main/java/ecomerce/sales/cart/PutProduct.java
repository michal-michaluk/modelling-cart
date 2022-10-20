package ecomerce.sales.cart;

import java.util.UUID;

public record PutProduct(UUID cartId, String productId, int amount) {
}
