package ecomerce.sales.delivery.methods;

import org.junit.jupiter.api.Test;

import static ecomerce.sales.delivery.methods.Dimensions.ofCentimeters;
import static ecomerce.sales.delivery.methods.ProductsFixture.*;
import static ecomerce.sales.delivery.methods.Weight.ofKiloGrams;
import static org.assertj.core.api.Assertions.assertThat;

class DeliveryMethodsPolicyTestDataBuilderDesignTest {

    DeliveryMethodsPolicy policy = new DeliveryMethodsPolicy();

    @Test
    void optionsNotLimited() {
        DeliveryMethods deliveryMethods = policy.deliveryMethodsFor(
                products(smallAndLight())
        );

        assertThat(deliveryMethods.inpost()).isTrue();
        assertThat(deliveryMethods.courier()).isTrue();
        assertThat(deliveryMethods.special()).isFalse();
    }

    @Test
    void inpostNotAllowed() {
        DeliveryMethods deliveryMethods = policy.deliveryMethodsFor(
                products(smallAndLight().butWith()
                        .dimensions(ofCentimeters(60, 66, 62)))
        );

        assertThat(deliveryMethods.inpost()).isFalse();
        assertThat(deliveryMethods.courier()).isTrue();
        assertThat(deliveryMethods.special()).isFalse();
    }

    @Test
    void tooHeavy() {
        DeliveryMethods deliveryMethods = policy.deliveryMethodsFor(
                products(smallAndLight().butWith()
                        .weight(ofKiloGrams(30)))
        );

        assertThat(deliveryMethods.inpost()).isFalse();
        assertThat(deliveryMethods.courier()).isFalse();
        assertThat(deliveryMethods.special()).isTrue();
    }

    @Test
    void specialRequired() {
        DeliveryMethods deliveryMethods = policy.deliveryMethodsFor(
                products(requiringSpecialDelivery())
        );

        assertThat(deliveryMethods.inpost()).isFalse();
        assertThat(deliveryMethods.courier()).isFalse();
        assertThat(deliveryMethods.special()).isTrue();
    }
}
