package ecomerce.sales.cart;

public interface ProductRepository {
    Cart.Product getAvailability(String productId);

    CartDetails.ProductDetails getDetails(String productId);
}
