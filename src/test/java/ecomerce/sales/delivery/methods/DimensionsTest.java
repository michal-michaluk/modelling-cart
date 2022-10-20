package ecomerce.sales.delivery.methods;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DimensionsTest {

    @Test
    void fitsIntoMax() {
        Dimensions max = Dimensions.ofCentimeters(40, 36, 62);
        Dimensions example = Dimensions.ofCentimeters(10, 10, 10);

        assertThat(example.fitsInto(max)).isTrue();
    }

    @Test
    void biggerThenMax() {
        Dimensions max = Dimensions.ofCentimeters(40, 36, 62);
        Dimensions example = Dimensions.ofCentimeters(70, 10, 10);

        assertThat(example.fitsInto(max)).isFalse();
    }

    @Test
    void fitsIntoMaxAfterRotation() {
        Dimensions max = Dimensions.ofCentimeters(40, 36, 62);
        Dimensions example = Dimensions.ofCentimeters(62, 40, 36);

        assertThat(example.fitsInto(max)).isTrue();
    }
}
