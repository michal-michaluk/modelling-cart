package ecomerce.sales.cart;

import ecomerce.sales.delivery.methods.DeliveryMethodsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
public class CartService {

    private final CartRepository carts;
    private final ProductRepository products;
    private final DeliveryMethodsService deliveryMethods;

    public CartDetails get(UUID cartId) {
        Cart cart = carts.get(cartId);
        var builder = CartDetails.builder();
        cart.forEach((amount, product) -> builder.add(
                products.getDetails(product.id()), amount
        ));
        return builder
                .deliveryMethods(deliveryMethods.deliveryMethodsFor(cart.productIds()))
                .build();
    }

    public void put(PutProduct command) {
        Cart cart = carts.get(command.cartId());
        Cart.Product product = products.getAvailability(command.productId());
        cart.put(product, command.amount());
        carts.save(cart);
    }

    public void remove(RemoveProduct command) {
        Cart cart = carts.get(command.cartId());
        cart.remove(command.productId());
        carts.save(cart);
    }
}
