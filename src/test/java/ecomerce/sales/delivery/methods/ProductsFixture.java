package ecomerce.sales.delivery.methods;

import ecomerce.sales.delivery.methods.Products.Product;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.stream.Stream;

public class ProductsFixture {

    public static ProductBuilder smallAndLight() {
        return new ProductBuilder()
                .dimensions(Dimensions.ofCentimeters(10, 20, 2))
                .weight(Weight.ofKiloGrams(1))
                .requiresSpecialDelivery(false);
    }

    public static ProductBuilder requiringSpecialDelivery() {
        return new ProductBuilder()
                .dimensions(Dimensions.ofCentimeters(60, 120, 60))
                .weight(Weight.ofKiloGrams(30))
                .requiresSpecialDelivery(true);
    }

    public static Products products(ProductBuilder... products) {
        return new Products(Stream.of(products).map(ProductBuilder::build).toList());
    }

    public static Products oneProductWith(Dimensions dimensions, Weight weight) {
        return new Products(List.of(new Product(dimensions, weight, false)));
    }

    public static Products oneProductWithSpecialDelivery() {
        return new Products(List.of(new Product(Dimensions.ofCentimeters(30, 30, 30), Weight.ofKiloGrams(54), true)));
    }

    @Setter
    @Accessors(fluent = true)
    public static class ProductBuilder {
        Dimensions dimensions;
        Weight weight;
        boolean requiresSpecialDelivery;

        public ProductBuilder butWith() {
            return this;
        }

        public Product build() {
            return new Product(dimensions, weight, requiresSpecialDelivery);
        }
    }
}
