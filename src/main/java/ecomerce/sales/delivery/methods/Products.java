package ecomerce.sales.delivery.methods;

import lombok.Builder;
import lombok.Singular;

import java.util.List;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;

@Builder
class Products {
    @Singular
    final List<Product> products;

    Products(List<Product> products) {
        this.products = products;
    }

    boolean anyMatches(Predicate<Product> predicate) {
        return products.stream().anyMatch(predicate);
    }

    boolean allMatches(Predicate<Product> predicate) {
        return products.stream().allMatch(predicate);
    }

    int sum(ToIntFunction<Product> function) {
        return products.stream().mapToInt(function).sum();
    }

    record Product(Dimensions dimensions, Weight weight, boolean requiresSpecialDelivery) {
        public boolean dimensionFitsInto(Dimensions dimensions) {
            return this.dimensions.fitsInto(dimensions);
        }
    }
}
