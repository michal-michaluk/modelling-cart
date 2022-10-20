package ecomerce.sales.cart;

import java.util.UUID;

public record RemoveProduct(UUID cartId, String productId) {
}
