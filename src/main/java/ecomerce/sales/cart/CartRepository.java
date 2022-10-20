package ecomerce.sales.cart;

import java.util.UUID;

public interface CartRepository {
    Cart get(UUID cartId);

    void save(Cart cart);
}
