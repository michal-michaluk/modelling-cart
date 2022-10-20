package ecomerce.sales.delivery.methods;

import java.util.stream.IntStream;

record Dimensions(int width, int height, int depth) {
    static Dimensions ofCentimeters(int width, int height, int depth) {
        return new Dimensions(width * 10, height * 10, depth * 10);
    }

    boolean fitsInto(Dimensions dimensions) {
        var d1 = IntStream.of(width, height, depth).sorted().toArray();
        var d2 = IntStream.of(dimensions.width, dimensions.height, dimensions.depth).sorted().toArray();
        for (int i = 0; i < 3; i++) {
            if (d1[i] > d2[i]) {
                return false;
            }
        }
        return true;
    }
}
